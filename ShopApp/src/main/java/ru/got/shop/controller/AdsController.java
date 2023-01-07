package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.api.AdsApi;
import ru.got.shop.dto.*;
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.AdsService;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@Slf4j
public class AdsController implements AdsApi {

    private final AdsService adsService;
    private final PermissionService permissionService;

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> addAd(AdCreateDto adCreateDto, MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adsService.addAd(adCreateDto, file));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> editPicture(Integer id, MultipartFile file) {
        permissionService.checkAllowedForbidden(id);
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
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<FullAdDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adsService.getFullAdDto(id));
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<Void> removeAd(Integer id) {
        permissionService.checkAllowedForbidden(id);
        adsService.removeAd(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('ads.crud')")
    public ResponseEntity<AdDto> updateAd(Integer id, AdCreateDto adCreateDto) {
        permissionService.checkAllowedForbidden(id);
        return ResponseEntity.ok(adsService.updateAd(id, adCreateDto));
    }

    @Override
    public ResponseEntity<List<AdDto>> getAdsByCriteria(@RequestBody AdCriteriaDto adCriteriaDto,
                                                        @RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return ResponseEntity.ok(adsService.getAdsByCriteria(adCriteriaDto, pageable));
    }
}
