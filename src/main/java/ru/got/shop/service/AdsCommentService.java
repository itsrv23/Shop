package ru.got.shop.service;

import ru.got.shop.openapi.dto.AdsComment;
import ru.got.shop.openapi.dto.ResponseWrapperAdsComment;

public interface AdsCommentService {
    AdsComment addAdsComments(String adPk, AdsComment comment);

    void deleteAdsComment(String adPk, Integer id);

    AdsComment getAdsComment(String adPk, Integer id);

    ResponseWrapperAdsComment getAdsComments(String adPk);

    AdsComment updateAdsComment(String adPk, Integer id, AdsComment comment);
}
