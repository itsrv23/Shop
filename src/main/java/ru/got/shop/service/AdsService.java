package ru.got.shop.service;

import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;

public interface AdsService {
    AdDto addAd(AdDto createAds);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getMyAds(Integer author_id);

    FullAdDto getAds(Integer id);

    AdDto removeAd(Integer id);

    AdDto updateAd(Integer id, AdDto adDto);
}
