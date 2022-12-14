package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.FullAdsMapper;
import ru.got.shop.mapper.PictureMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.AdsService;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService, AuthenticationFacade {

    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final AdsRepository adsRepository;
    private final String NOT_FOUND = "Ads doesn't exist!!!";
    private final UserRepository userRepository;
    @Qualifier("pictureDiskServiceImpl")
    private final PictureService pictureService;
    private final PictureMapper pictureMapper;

    @Override
    public AdDto addAd(AdCreateDto adCreateDto, MultipartFile file) {
        try {
            User user = getUser();
            Picture picture = pictureMapper.mapToPicture(file);
            picture = pictureService.download(picture);
            Ads ads = adsMapper.buildAds(user, adCreateDto, picture);
            Ads ads1 = adsRepository.save(ads);
            return adsMapper.toDto(ads1);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while picture reading ", e);
        }
    }

    @Override
    public byte[] getImageById(String uuid) {
        return pictureService.upload(UUID.fromString(uuid));
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ads> ads = adsRepository.findAll();
        List<AdDto> adDtoList = adsMapper.toDtos(ads);
        return new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
    }

    @Transactional
    @Override
    public ResponseWrapperAdsDto getMyAds() {
        List<Ads> adsList = adsRepository.findAllByUserId(getUser());
        if (adsList.isEmpty()) {
            throw new EntityNotFoundException("There is an empty myAdsList.");
        }
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        return new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
    }

    @Override
    public FullAdDto getFullAdDto(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        User user = userRepository.findById(ads.getUserId().getId()).orElseThrow(() -> new UserNotFoundException(id));
        return fullAdsMapper.toDto(user, ads);
    }

    @Override
    public void removeAd(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        adsRepository.delete(ads);
    }

    @Override
    public AdDto updateAd(Integer id, AdCreateDto adCreateDto) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        ads = adsMapper.updateAds(adCreateDto, ads);
        return adsMapper.toDto(adsRepository.save(ads));
    }

    @Override
    public AdDto updatePicture(Integer id, MultipartFile file) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        Picture picture;
        try {
            picture = pictureMapper.mapToPicture(file);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong wile picture reading", e);
        }
        if (ads.getPicture() == null) {
            Picture pictureNew = pictureService.download(picture);
            ads.setPicture(pictureNew);
            adsRepository.save(ads);
        } else {
            UUID uuid = ads.getPicture().getUuid();
            pictureService.update(uuid, picture);
            adsRepository.save(ads);
        }
        return adsMapper.toDto(ads);
    }

    private User getUser() {
        String login = getLogin();
        return userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
    }
}
