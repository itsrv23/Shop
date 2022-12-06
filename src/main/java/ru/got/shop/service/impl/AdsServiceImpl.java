package ru.got.shop.service.impl;

import ru.got.shop.openapi.dto.*;
import ru.got.shop.service.AdsService;
import ru.got.shop.service.AdsUtil;

public class AdsServiceImpl implements AdsService {

    @Override
    public AdsComment addAdsComments(String adPk, AdsComment comment) {
        return AdsUtil.getAdsComment();
    }

    @Override
    public Ads addAds(CreateAds createAds) {
        return AdsUtil.getAds();
    }

    @Override
    public void deleteAdsComment(String adPk, Integer id) {
    }

    @Override
    public ResponseWrapperAds getALLAds() {
        return AdsUtil.getResponseWrapperAds();
    }

    @Override
    public AdsComment getAdsComment(String adPk, Integer id) {
        return AdsUtil.getAdsComment();
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(String adPk) {
        return AdsUtil.getResponseWrapperAdsComment();
    }

    @Override
    public ResponseWrapperAds getAdsMe(Boolean authenticated,
                                       String authorities0Authority,
                                       Object credentials,
                                       Object details,
                                       Object principal) {
        return AdsUtil.getResponseWrapperAds();
    }

    @Override
    public FullAds getAds(Integer id) {
        return AdsUtil.getFullAds();
    }

    @Override
    public void removeAdsUsingDELETE(Integer id) {
    }

    @Override
    public AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment) {
        return AdsUtil.getAdsComment();
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        return AdsUtil.getAds();
    }
}
