package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.FullAdsMapper;
import ru.got.shop.mapper.PictureMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.AdsService;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AdsServiceImpl implements AdsService, AuthenticationFacade {

    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final AdsRepository adsRepository;
    private final String NOT_FOUND = "Ads doesn't exist!!!";

    private final UserRepository userRepository;
    private final PictureService pictureService;
    private final PictureMapper pictureMapper;

    @Override
    public AdDto addAd(AdDto adDto, MultipartFile file) {
        List<Ads> ads = adsRepository.findByTitleAndPrice(adDto.getTitle(), adDto.getPrice());
        if (ads.isEmpty()) {
            log.debug("POST: /ads  adDto from service:: {}", adDto);
            adDto.setAuthor(getUser().getId());
            String uuid = pictureService.download(file, null).toString();
            adDto.setImage(uuid);
            log.debug(" adDto before save:: {}", adDto);
            adDto = adsMapper.toDto(adsRepository.save(adsMapper.toEntity(adDto)));
        } else {
            String exist = "The ads already exists!!!";
            throw new IllegalArgumentException(exist);
        }
        return adDto;
    }

    @Override
    public byte[] getImageById(String uuid) {
        return pictureService.upload(UUID.fromString(uuid));
    }

    @Override
    public ResponseWrapperAdsDto getAllByTitle(String title) {
        List<AdDto> allByTitleLike = adsMapper.toDtos(adsRepository.findAllByTitleStartingWithIgnoreCase(title));
        return new ResponseWrapperAdsDto(allByTitleLike.size(), allByTitleLike);
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ads> ads = adsRepository.findAll();
        List<AdDto> adDtoList = adsMapper.toDtos(ads);
        log.debug("SERVICE DTO LIST{}", adDtoList);
        return new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
    }

    @Override
    public ResponseWrapperAdsDto getMyAds() {
        List<Ads> adsList = adsRepository.findAllByUserId(getUser());
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        ResponseWrapperAdsDto wrapperAdsDto = new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
        log.debug("GET  /ads/me object:: {}", wrapperAdsDto);
        return wrapperAdsDto;
    }

    @Override
    public FullAdDto getFullAdDto(Integer id) {
        User user = userRepository.findFirstByEmail(getLogin())
                .orElseThrow(() -> new EntityNotFoundException(UserServiceImpl.NOT_EXIST));
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        return fullAdsMapper.toDto(user, ads);
    }

    @Override
    public AdDto removeAd(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        AdDto adDto = adsMapper.toDto(ads);
        log.debug("DELETE  /ads/{id} object:: {}\"", adDto.toString());
        adsRepository.delete(ads);
        return adDto;
    }

    @Override
    public AdDto updateAd(Integer id, AdDto adDto) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        AdDto dtoToSave = adsMapper.updateDto(adDto, ads);
        log.debug("PATCHED TO SAVE {}", dtoToSave);
        ads = adsMapper.toEntity(dtoToSave);
        return adsMapper.toDto(adsRepository.save(ads));
    }

    @Override
    public AdDto updatePicture(Integer adId, MultipartFile file) {
        try {
            Ads ads = adsRepository.findById(adId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
            pictureService.download(file, ads.getPicture().getUuid());
            Picture picture = pictureMapper.mapToPicture(file, ads.getPicture().getUuid());
            ads.setPicture(picture);
            return adsMapper.toDto(adsRepository.save(ads));
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong wile picture reading", e);
        }
    }

    private User getUser() {
        return userRepository.findFirstByEmail(getLogin())
                .orElseThrow(() -> new EntityNotFoundException(UserServiceImpl.NOT_EXIST));
    }
}
