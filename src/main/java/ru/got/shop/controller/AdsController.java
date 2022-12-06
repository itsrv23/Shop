package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.openapi.controller.AdsApi;
import ru.got.shop.openapi.dto.*;
import ru.got.shop.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsController implements AdsApi {

    private final AdsService adsService;

    @Override
    public ResponseEntity<AdsComment> addAdsCommentsUsingPOST(String adPk, AdsComment comment) {
        return ResponseEntity.ok(adsService.addAdsComments(adPk, comment));
    }

    @Override
    public ResponseEntity<Ads> addAdsUsingPOST(CreateAds createAds) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adsService.addAds(createAds));
    }

    @Override
    public ResponseEntity<Void> deleteAdsCommentUsingDELETE(String adPk, Integer id) {
        adsService.deleteAdsComment(adPk, id);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getALLAdsUsingGET() {
        return ResponseEntity.ok(adsService.getALLAds());
    }

    @Override
    public ResponseEntity<AdsComment> getAdsCommentUsingGET(String adPk, Integer id) {
        return ResponseEntity.ok(adsService.getAdsComment(adPk, id));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsComment> getAdsCommentsUsingGET(String adPk) {
        return ResponseEntity.ok(adsService.getAdsComments(adPk));
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(Boolean authenticated,
                                                               String authorities0Authority,
                                                               Object credentials,
                                                               Object details,
                                                               Object principal) {
        return ResponseEntity.ok(adsService.getAdsMe(authenticated,
                authorities0Authority,
                credentials,
                details,
                principal));
    }

    @PreAuthorize("hasAuthority( 'users.read')")
    @Override
    public ResponseEntity<FullAds> getAdsUsingGET(Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @PreAuthorize("hasAuthority('users.read:write')")
    @Override
    public ResponseEntity<Void> removeAdsUsingDELETE(Integer id) {
        adsService.removeAdsUsingDELETE(id);
        return ResponseEntity.ok()
                .build();
    }

    @Override
    public ResponseEntity<AdsComment> updateAdsCommentUsingPATCH(String adPk, Integer id, AdsComment comment) {
        return ResponseEntity.ok(adsService.updateAdsComment(adPk, id, comment));
    }

    @Override
    public ResponseEntity<Ads> updateAdsUsingPATCH(Integer id, Ads ads) {
        return ResponseEntity.ok(adsService.updateAds(id, ads));
    }
}
