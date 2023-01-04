package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;

public interface AdsService {
    AdDto addAd(AdCreateDto adCreateDto, MultipartFile file);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getMyAds();

    FullAdDto getFullAdDto(Integer id);

    void removeAd(Integer id);

    AdDto updateAd(Integer id, AdCreateDto adCreateDto);

    AdDto updatePicture(Integer adId, MultipartFile file);

    byte[] getImageById(String uuid);
}
