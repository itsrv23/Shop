package ru.got.shop.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.controller.AdsApi;
import ru.got.shop.model.dto.Ads;
import ru.got.shop.model.dto.CreateAds;
import ru.got.shop.model.dto.FullAds;
import ru.got.shop.model.dto.ResponseWrapperAds;
import ru.got.shop.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsController implements AdsApi {

    private final AdsService adsService;

    @Override
    public ResponseEntity<Ads> addAdsUsingPOST(CreateAds createAds) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adsService.addAds(createAds));
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getALLAdsUsingGET() {
        return ResponseEntity.ok(adsService.getALLAds());
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
    public ResponseEntity<Ads> updateAdsUsingPATCH(Integer id, Ads ads) {
        return ResponseEntity.ok(adsService.updateAds(id, ads));
    }
}
