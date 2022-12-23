package ru.got.shop.service;

import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

public interface AdsCommentService {
    AdsCommentDto addAdsComment(Integer adId, AdsCommentDto comment);

    AdsCommentDto deleteAdsComment(Integer adId, Integer id);

    AdsCommentDto getAdsComment(Integer adId, Integer id);

    ResponseWrapperAdsCommentDto getAdsComments(Integer adId);

    AdsCommentDto updateAdsComment(Integer adId, Integer id, AdsCommentDto comment);
}
