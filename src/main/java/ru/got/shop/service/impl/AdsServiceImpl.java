package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.mapper.AdsMapper;
import ru.got.shop.mapper.ResponseWrapperAdsMapper;
import ru.got.shop.mapper.UserMapper;
import ru.got.shop.openapi.dto.*;
import ru.got.shop.repository.AdsRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.AdsService;
import ru.got.shop.constant.AdsFactory;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdsServiceImpl implements AdsService {

    private final UserRepository userRepository;
    private final ResponseWrapperAdsMapper responseWrapperAdsMapper;
    private final  AdsMapper adsMapper;

    @Override
    public AdsComment addAdsComments(String adPk, AdsComment comment) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public Ads addAds(CreateAds createAds) {
        System.out.println("createAds = " + createAds);
        return AdsFactory.getAds();
    }

    @Override
    public void deleteAdsComment(String adPk, Integer id) {
    }

    @Override
    public ResponseWrapperAds getALLAds() {
        ru.got.shop.model.User user = userRepository.findById(1).orElseThrow(UserNotFoundException::new);
        List<ru.got.shop.model.Ads> ads = user.getAds();

        ResponseWrapperAds responseWrapperAds = responseWrapperAdsMapper.toDto(ads.size(), adsMapper.toDtos(ads));
        log.info(responseWrapperAds.toString());
        log.info(AdsFactory.getResponseWrapperAds().toString());
//        return AdsFactory.getResponseWrapperAds();
        return responseWrapperAds;
    }

    @Override
    public AdsComment getAdsComment(String adPk, Integer id) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public ResponseWrapperAdsComment getAdsComments(String adPk) {
        return AdsFactory.getResponseWrapperAdsComment();
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
    public AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment) {
        return AdsFactory.getAdsComment();
    }

    @Override
    public Ads updateAds(Integer id, Ads ads) {
        return AdsFactory.getAds();
    }
}
