package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.mapper.*;
import ru.got.shop.openapi.dto.*;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.AdsService;
import ru.got.shop.constant.AdsFactory;
import ru.got.shop.exception.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final UserRepository userRepository;
    private final ResponseWrapperAdsMapper responseWrapperAdsMapper;
    private final UserMapper userMapper;
    private final AdsMapper adsMapper;

    @Override
    public Ads addAds(CreateAds createAds) {
        System.out.println("createAds = " + createAds);
        return AdsFactory.getAds();
    }

    @Override
    public ResponseWrapperAds getALLAds() {
        ru.got.shop.model.User user = userRepository.findById(1).orElseThrow(UserNotFoundException::new);
        List<ru.got.shop.model.Ads> ads = user.getAds();

        ResponseWrapperAds responseWrapperAds = responseWrapperAdsMapper.toDto(ads.size(), adsMapper.toDtos(ads));
        log.info(responseWrapperAds.toString());
//        log.info(AdsFactory.getResponseWrapperAds().toString());
//        return AdsFactory.getResponseWrapperAds();
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
    public FullAds getAds(Integer id) {
        return AdsFactory.getFullAds();
    }

    @Override
    public void removeAdsUsingDELETE(Integer id) {
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        return AdsFactory.getAds();
    }
}
