package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.model.dto.AdCreateDto;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;

public interface AdsService {
    AdDto addAd(AdCreateDto adCreateDto, MultipartFile file);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getMyAds();

    FullAdDto getFullAdDto(Integer id);

    AdDto removeAd(Integer id);

    AdDto updateAd(Integer id, AdCreateDto adCreateDto);

    AdDto updatePicture(Integer adId, MultipartFile file);

    byte[] getImageById(String uuid);
}
