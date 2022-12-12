package ru.got.shop.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.controller.AdsApi;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;
import ru.got.shop.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class AdsController implements AdsApi {

    private final AdsService adsService;

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Override
    public ResponseEntity<AdDto> addAd(AdDto adDto) {
        return ResponseEntity.ok(adsService.addAd(adDto));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds(Integer authorId) {
        return ResponseEntity.ok(adsService.getMyAds(authorId));
    }

    @Override
    public ResponseEntity<FullAdDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @Override
    public ResponseEntity<AdDto> removeAd(Integer id) {
        return ResponseEntity.ok(adsService.removeAd(id));
    }

    @Override
    public ResponseEntity<AdDto> updateAd(Integer id, AdDto adDto) {
        return ResponseEntity.ok(adsService.updateAd(id, adDto));
    }
}
