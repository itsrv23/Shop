package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.FullAdsMapper;
import ru.got.shop.model.Ads;
import ru.got.shop.model.FullAds;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.model.dto.FullAd;
import ru.got.shop.model.dto.ResponseWrapperAds;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.service.AdsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final AdsRepository adsRepository;
    private final String NOT_FOUND = "Ads doesn't exist!!!";
    private final String EXIST = "The ads already exists!!!";

    @Override
    public AdDto addAd(AdDto adDto) {
        List<Ads> ads = adsRepository.findByTitleAndPrice(adDto.getTitle(), adDto.getPrice());
        if (ads.isEmpty()) {
            adsRepository.save(adsMapper.toEntity(adDto));
        } else {
            throw new IllegalArgumentException(EXIST);
        }
        return adDto;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        List<Ads> ads = adsRepository.findAll();
        List<AdDto> adDtoList = adsMapper.toDtos(ads);
        return new ResponseWrapperAds(adDtoList.size(), adDtoList);
    }

    @Override
    public ResponseWrapperAds getMyAds(Integer authorId) {
        List<Ads> adsList = adsRepository.findAllAdsByAuthorId(authorId);
        List<AdDto> adDtoList = adsMapper.toDtos(adsList);
        return new ResponseWrapperAds(adDtoList.size(), adDtoList);
    }

    @Override
    public FullAd getAds(Integer id) {
        FullAds fullAdsDto = adsRepository.getFullAds(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        return fullAdsMapper.toDto(fullAdsDto);
    }

    @Override
    public AdDto removeAd(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        adsRepository.deleteById(id);
        return adsMapper.toDto(ads);
    }

    @Override
    public AdDto updateAd(Integer id, AdDto adDto) {
        Ads ads = adsRepository.findById(adDto.getPk()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND));
        AdDto adDtoTmp = adsMapper.toDto(ads);
        if (adDtoTmp.equals(adDto))
            throw new IllegalArgumentException(EXIST);
        ads = adsMapper.toEntity(adDto);
        adsRepository.save(ads);
        return adDto;
    }
}
