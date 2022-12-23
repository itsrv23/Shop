package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.api.AdsCommentApi;
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.service.AdsCommentService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsCommentController implements AdsCommentApi {

    private final AdsCommentService adsCommentService;

    @Override
    public ResponseEntity<AdsCommentDto> addAdsComment(Integer adId, AdsCommentDto comment) {
        return ResponseEntity.ok(adsCommentService.addAdsComment(adId, comment));
    }

    @Override
    public ResponseEntity<AdsCommentDto> deleteAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adsCommentService.deleteAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<AdsCommentDto> getAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adsCommentService.getAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(Integer adId) {
        return ResponseEntity.ok(adsCommentService.getAdsComments(adId));
    }

    @Override
    public ResponseEntity<AdsCommentDto> updateAdsComment(Integer adId, Integer id, AdsCommentDto comment) {
        return ResponseEntity.ok(adsCommentService.updateAdsComment(adId, id, comment));
    }
}
