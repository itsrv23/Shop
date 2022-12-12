package ru.got.shop.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.controller.AdsApi;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAd;
import ru.got.shop.model.dto.FullAds;
import ru.got.shop.model.dto.ResponseWrapperAds;
import ru.got.shop.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsController implements AdsApi {

    private final AdsService adsService;

    @Override
    public ResponseEntity<ResponseWrapperAds> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Override
    public ResponseEntity<AdDto> addAd(AdDto adDto) {
        return ResponseEntity.ok(adsService.addAd(adDto));
    }

    @Override
    public ResponseEntity<ResponseWrapperAds> getMyAds(Integer authorId) {
        return ResponseEntity.ok(adsService.getMyAds(authorId));
    }

    @Override
    public ResponseEntity<FullAd> getFullAd(Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }
//    @Override
//    public ResponseEntity<AdsComment> getAdsCommentUsingGET(String adPk, Integer id) {
//        return ResponseEntity.ok(adsCommentService.getAdsComment(adPk, id));
//    }
//    @Override
//    public ResponseEntity<ResponseWrapperAdsComment> getAdsCommentsUsingGET(String adPk) {
//        return ResponseEntity.ok(adsCommentService.getAdsComments(adPk));
//    }
//
//    @Override
//    public ResponseEntity<ResponseWrapperAds> getAdsMeUsingGET(Boolean authenticated,
//                                                               String authorities0Authority,
//                                                               Object credentials,
//                                                               Object details,
//                                                               Object principal) {
//        return ResponseEntity.ok(adsService.getAdsMe(authenticated,
//                authorities0Authority,
//                credentials,
//                details,
//                principal));
//    }

    //    @PreAuthorize("hasAuthority( 'users.read')")
//    @Override
//    public ResponseEntity<FullAds> getAdsUsingGET(Integer id) {
//        @Override public ResponseEntity<FullAd> getFullAd (Integer id){
//            return ResponseEntity.ok(adsService.getAds(id));
//        }
    @Override
    public ResponseEntity<AdDto> removeAd(Integer id) {
        return ResponseEntity.ok(adsService.removeAd(id));
    }

    @Override
    public ResponseEntity<AdDto> updateAd(Integer id, AdDto adDto) {
        return ResponseEntity.ok(adsService.updateAd(id, adDto));
    }
}
