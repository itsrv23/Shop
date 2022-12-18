package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;

public interface AdsService {
    AdDto addAd(AdDto adDto, MultipartFile file);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getMyAds();

    FullAdDto getAds(Integer id);

    AdDto removeAd(Integer id);

    AdDto updateAd(Integer id, AdDto adDto);

    byte[] getImageById(String uuid);
}
