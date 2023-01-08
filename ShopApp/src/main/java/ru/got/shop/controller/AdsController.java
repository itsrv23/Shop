package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.AdService;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdsController implements AdsApi {

    private final AdService adService;
    private final PermissionService permissionService;

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> addAd(AdCreateDto adCreateDto, MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.addAd(adCreateDto, file));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> editPicture(Integer id, MultipartFile file) {
        permissionService.checkAllowedForbidden(id);
        return ResponseEntity.ok(adService.updatePicture(id, file));
    }

    @Override
    public ResponseEntity<byte[]> getAdImage(String uuid) {
        return ResponseEntity.ok(adService.getImageById(uuid));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        return ResponseEntity.ok(adService.getMyAds());
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<FullAdDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adService.getFullAdDto(id));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<Void> removeAd(Integer id) {
        permissionService.checkAllowedForbidden(id);
        adService.removeAd(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> updateAd(Integer id, AdCreateDto adCreateDto) {
        permissionService.checkAllowedForbidden(id);
        return ResponseEntity.ok(adService.updateAd(id, adCreateDto));
    }
}
