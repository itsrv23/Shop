package ru.got.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.openapi.controller.AdsApi;
import ru.got.shop.openapi.dto.*;

@RestController
public class AdsController implements AdsApi {
    @Override
    public ResponseEntity<AdsComment> addAdsCommentsUsingPOST(String adPk, AdsComment comment) {
        return null;
    }

    @Override
    public ResponseEntity<Ads> addAdsUsingPOST(CreateAds createAds) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteAdsCommentUsingDELETE(String adPk, Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getALLAdsUsingGET() {
        return null;
    }

    @Override
    public ResponseEntity<AdsComment> getAdsCommentUsingGET(String adPk, Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsComment> getAdsCommentsUsingGET(String adPk) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(Boolean authenticated, String authorities0Authority, Object credentials, Object details, Object principal) {
        return null;
    }

    @Override
    public ResponseEntity<FullAds> getAdsUsingGET(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeAdsUsingDELETE(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<AdsComment> updateAdsCommentUsingPATCH(String adPk, Integer id, AdsComment comment) {
        return null;
    }

    @Override
    public ResponseEntity<Ads> updateAdsUsingPATCH(Integer id, Ads ads) {
        return null;
    }
}
