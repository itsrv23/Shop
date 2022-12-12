package ru.got.shop.service;

import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAd;
import ru.got.shop.model.dto.ResponseWrapperAds;

public interface AdsService {
    AdDto addAd(AdDto createAds);

    ResponseWrapperAds getAllAds();

    ResponseWrapperAds getMyAds(Integer author_id);

    FullAd getAds(Integer id);

    AdDto removeAd(Integer id);

    AdDto updateAd(Integer id, AdDto adDto);
}
