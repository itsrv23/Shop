package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.constant.AdsFactory;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.FullAdsMapper;
import ru.got.shop.mapper.ResponseWrapperAdsMapper;
import ru.got.shop.model.FullAds;
import ru.got.shop.openapi.dto.Ads;
import ru.got.shop.openapi.dto.CreateAds;
import ru.got.shop.openapi.dto.ResponseWrapperAds;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.AdsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final UserRepository userRepository;
    private final ResponseWrapperAdsMapper responseWrapperAdsMapper;
    private final AdsMapper adsMapper;
    private final FullAdsMapper fullAdsMapper;
    private final AdsRepository adsRepository;

    @Override
    public Ads addAds(CreateAds createAds) {
        System.out.println("createAds = " + createAds);
        return AdsFactory.getAds();
    }

    @Override
    public ResponseWrapperAds getALLAds() {
        ru.got.shop.model.User user = userRepository.findById(1)
                .orElseThrow(() -> new EntityNotFoundException(UserServiceImpl.NOT_EXIST));
        List<ru.got.shop.model.Ads> ads = user.getAds();
        ResponseWrapperAds responseWrapperAds = responseWrapperAdsMapper.toDto(ads.size(), adsMapper.toDtos(ads));
        log.info(responseWrapperAds.toString());
        return responseWrapperAds;
    }

    @Override
    public ResponseWrapperAds getAdsMe(Boolean authenticated,
                                       String authorities0Authority,
                                       Object credentials,
                                       Object details,
                                       Object principal) {
        return AdsFactory.getResponseWrapperAds();
    }

    @Override
    public ru.got.shop.openapi.dto.FullAds getAds(Integer id) {
        FullAds fullAdsDto = adsRepository.getFullAds(id)
                .orElseThrow(() -> new EntityNotFoundException("Ads doesn't exist!!!"));
        return fullAdsMapper.toDto(fullAdsDto);
    }

    @Override
    public void removeAdsUsingDELETE(Integer id) {
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        return AdsFactory.getAds();
    }
}
