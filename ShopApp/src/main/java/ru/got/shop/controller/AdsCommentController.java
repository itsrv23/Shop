package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.api.AdsCommentApi;
import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;
import ru.got.shop.service.AdCommentService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsCommentController implements AdsCommentApi {

    private final AdCommentService adCommentService;

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud', 'ads.comment.full')")
    public ResponseEntity<AdCommentDto> addAdsComment(Integer adId, AdCommentDto comment) {
        return ResponseEntity.ok(adCommentService.addAdsComment(adId, comment));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud', 'ads.comment.full')")
    public ResponseEntity<AdCommentDto> deleteAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adCommentService.deleteAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<AdCommentDto> getAdsComment(Integer adId, Integer id) {
        return ResponseEntity.ok(adCommentService.getAdsComment(adId, id));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsCommentDto> getAdsComments(Integer adId) {
        return ResponseEntity.ok(adCommentService.getAdsComments(adId));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ads.comment.crud', 'ads.comment.full')")
    public ResponseEntity<AdCommentDto> updateAdsComment(Integer adId, Integer id, AdCommentDto comment) {
        return ResponseEntity.ok(adCommentService.updateAdsComment(adId, id, comment));
    }
}
