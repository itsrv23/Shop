package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.FullAdsMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAd;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.AdsService;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService, AuthenticationFacade {

    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final AdsRepository adsRepository;
    private final String NOT_FOUND = "Ads doesn't exist!!!";
    private final String EXIST = "The ads already exists!!!";

    private final UserRepository userRepository;
    private final PictureService pictureService;
    @Value("${pic.path}")
    private String path;

    @Override
    public AdDto addAd(AdDto adDto, MultipartFile file) {
        List<Ads> ads = adsRepository.findByTitleAndPrice(adDto.getTitle(), adDto.getPrice());
        if (ads.isEmpty()) {
            log.debug("adDto from service:: {}", adDto);
            adDto.setAuthor(getAuthorId());
            adDto = adsMapper.toDto(adsRepository.save(adsMapper.toEntity(adDto)));
            log.debug("adDto from service:: {}", adDto);
            String uuid = pictureService.download(file, adDto).toString();
            adDto.setImage(getPath(uuid));
            adsRepository.save(adsMapper.toEntity(adDto));
        } else {
            throw new IllegalArgumentException(EXIST);
        }
        return adDto;
    }

    @Override
    public byte[] getImageById(String uuid) {
        Ads ads = adsRepository.findByImage(getPath(uuid)).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        return pictureService.upload(ads.getPk());
    }

    @Override
    public ResponseWrapperAdsDto getAllAds() {
        List<Ads> ads = adsRepository.findAll();
        List<AdDto> adDtoList = adsMapper.toDtos(ads);
        return new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
    }

    @Override
    public ResponseWrapperAdsDto getMyAds() {
        List<Ads> adsList = adsRepository.findAllAdsByAuthorId(getAuthorId());
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        ResponseWrapperAdsDto wrapperAdsDto = new ResponseWrapperAdsDto(adDtoList.size(), adDtoList);
        log.debug("GET  /ads/me object:: {}", wrapperAdsDto);
        return wrapperAdsDto;
    }

    @Override
    public FullAdDto getAds(Integer id) {
        FullAd fullAd = adsRepository.getFullAds(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        FullAdDto fullAdsDto = fullAdsMapper.toDto(fullAd);
//        Ads ads = adsRepository.findById(id).orElseThrow(()->new EntityNotFoundException(NOT_FOUND));
//       AdDto ad=  adsMapper.toDto(ads);
        log.debug("GET  /ads/{id} object:: {}", fullAdsDto.toString());
        return fullAdsMapper.toDto(fullAdsDto);
//        return ad;
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
        AdDto adDtoTmp = adsMapper.toDto(ads);
        adDto.setAuthor(ads.getUserId().getId());
        adDto.setPk(ads.getPk());
        if (adDtoTmp.equals(adDto))
            throw new IllegalArgumentException(EXIST);
        ads = adsMapper.toEntity(adDto);
        log.debug(ads.toString());
        adsRepository.save(ads);
        log.debug("PATCH  /ads/{id} object:: {}", adDto);
        return adDto;
    }

    private int getAuthorId() {
        return userRepository.findFirstByEmail(getLogin())
                .orElseThrow(() -> new EntityNotFoundException(UserServiceImpl.NOT_EXIST))
                .getId();
    }

    private String getPath(String uuid) {
        return path.concat(uuid);
    }
}
