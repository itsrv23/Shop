package ru.got.shop.service.impl;

import org.springframework.stereotype.Service;
import ru.got.shop.openapi.dto.*;
import ru.got.shop.service.AdsService;
import ru.got.shop.constant.AdsFactory;
@Service
public class AdsServiceImpl implements AdsService {

    @Override
    public AdsComment addAdsComments(String adPk, AdsComment comment) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public Ads addAds(CreateAds createAds) {
        return AdsFactory.getAds();
    }

    @Override
    public void deleteAdsComment(String adPk, Integer id) {
    }

    @Override
    public ResponseWrapperAds getALLAds() {
        return AdsFactory.getResponseWrapperAds();
    }

    @Override
    public AdsComment getAdsComment(String adPk, Integer id) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(String adPk) {
        return AdsFactory.getResponseWrapperAdsComment();
    }

    @Override
    public ResponseWrapperAds getAdsMe(Boolean authenticated,
                                       String authorities0Authority,
                                       Object credentials,
                                       Object details,
                                       Object principal) {
        return AdsFactory.getResponseWrapperAds();
    }

    @Override
    public FullAds getAds(Integer id) {
        return AdsFactory.getFullAds();
    }

    @Override
    public void removeAdsUsingDELETE(Integer id) {
    }

    @Override
    public AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        return AdsFactory.getAds();
    }
}
