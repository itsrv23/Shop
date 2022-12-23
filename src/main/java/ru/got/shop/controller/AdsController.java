package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.api.AdsApi;
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;
import ru.got.shop.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdsController implements AdsApi {

    private final AdsService adsService;

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> addAd(AdCreateDto adCreateDto, MultipartFile file) {
        return ResponseEntity.ok(adsService.addAd(adCreateDto, file));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> editPicture(Integer id, MultipartFile file) {
        return ResponseEntity.ok(adsService.updatePicture(id, file));
    }

    @Override
    public ResponseEntity<byte[]> getAdImage(String uuid) {
        return ResponseEntity.ok(adsService.getImageById(uuid));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        return ResponseEntity.ok(adsService.getMyAds());
    }

    @Override
    public ResponseEntity<FullAdDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adsService.getFullAdDto(id));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> removeAd(Integer id) {
        return ResponseEntity.ok(adsService.removeAd(id));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> updateAd(Integer id, AdCreateDto adCreateDto) {
        return ResponseEntity.ok(adsService.updateAd(id, adCreateDto));
    }
}
