package ru.got.shop.service;

import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

public interface AdCommentService {
    AdCommentDto addAdsComment(Integer adId, AdCommentDto comment);

    AdCommentDto deleteAdsComment(Integer adId, Integer id);

    AdCommentDto getAdsComment(Integer adId, Integer id);

    ResponseWrapperAdsCommentDto getAdsComments(Integer adId);

    AdCommentDto updateAdsComment(Integer adId, Integer id, AdCommentDto comment);
}
