package ru.got.shop.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.controller.AdsApi;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAdDto;
import ru.got.shop.model.dto.ResponseWrapperAdsDto;
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
    public ResponseEntity<AdDto> addAd(AdDto adDto, MultipartFile file) {
        log.debug("POST /ads :: {}", adDto);
        log.debug("POST /ads :: {}", file.getOriginalFilename());
        return ResponseEntity.ok(adsService.addAd(adDto, file));
    }

    @Override
    public ResponseEntity<AdDto> editPicture(Integer id, MultipartFile file) {
        log.debug("PATCH /ads/{id}/image :: {}", id);
        log.debug("PATCH /ads/{id}/image :: {}", file.getOriginalFilename());
        return ResponseEntity.ok(adsService.updatePicture(id, file));
    }

    @Override
    public ResponseEntity<byte[]> getAdImage(String uuid) {
        return ResponseEntity.ok(adsService.getImageById(uuid));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> getMyAds() {
        return ResponseEntity.ok(adsService.getMyAds());
    }

    @Override
    public ResponseEntity<FullAdDto> getFullAd(Integer id) {
        return ResponseEntity.ok(adsService.getFullAdDto(id));
    }

    @Override
    public ResponseEntity<AdDto> removeAd(Integer id) {
        return ResponseEntity.ok(adsService.removeAd(id));
    }

    @Override
    public ResponseEntity<AdDto> updateAd(Integer id, AdDto adDto) {
        log.debug(" PATCH :: /ads/{id}  {}", id);
        log.debug(" PATCH :: /ads/{id}adDto {}", adDto);
        return ResponseEntity.ok(adsService.updateAd(id, adDto));
    }

    @Override
    public ResponseEntity<ResponseWrapperAdsDto> searchAllByTitleLike(String title) {
        return ResponseEntity.ok(adsService.getAllByTitle(title));
    }
}
