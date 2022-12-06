package ru.got.shop.service;

import ru.got.shop.openapi.dto.*;

public interface AdsService {
    AdsComment addAdsComments(String adPk, AdsComment comment);

    Ads addAds(CreateAds createAds);

    void deleteAdsComment(String adPk, Integer id);

    ResponseWrapperAds getALLAds();

    AdsComment getAdsComment(String adPk, Integer id);

    ResponseWrapperAdsComment getAdsComments(String adPk);

    ResponseWrapperAds getAdsMe(Boolean authenticated,
                                String authorities0Authority,
                                Object credentials,
                                Object details,
                                Object principal);

    FullAds getAds(Integer id);

    void removeAdsUsingDELETE(Integer id);

    AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment);

    Ads updateAds(Integer id, Ads ads);
}
