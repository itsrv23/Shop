package ru.got.shop.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.dto.*;

import java.util.List;

public interface AdService {
    AdDto addAd(AdCreateDto adCreateDto, MultipartFile file);

    ResponseWrapperAdsDto getAllAds();

    ResponseWrapperAdsDto getMyAds();

    FullAdDto getFullAdDto(Integer id);

    void removeAd(Integer id);

    AdDto updateAd(Integer id, AdCreateDto adCreateDto);

    AdDto updatePicture(Integer adId, MultipartFile file);

    byte[] getImageById(String uuid);

    List<AdDto> getAdsByCriteria(AdCriteriaDto adCriteriaDto, Pageable pageable);
}
