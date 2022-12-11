package ru.got.shop.service;

import ru.got.shop.model.dto.Ads;
import ru.got.shop.model.dto.CreateAds;
import ru.got.shop.model.dto.FullAds;
import ru.got.shop.model.dto.ResponseWrapperAds;

public interface AdsService {
    Ads addAds(CreateAds createAds);

    ResponseWrapperAds getALLAds();

    ResponseWrapperAds getAdsMe(Boolean authenticated,
                                String authorities0Authority,
                                Object credentials,
                                Object details,
                                Object principal);

    FullAds getAds(Integer id);

    void removeAdsUsingDELETE(Integer id);

    Ads updateAds(Integer id, Ads ads);
}
