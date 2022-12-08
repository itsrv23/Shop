package ru.got.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.openapi.dto.AdsComment;
import ru.got.shop.openapi.dto.ResponseWrapperAdsComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperAdsCommentMapper {

    @Mapping(source = "count", target ="count")
    @Mapping(source = "adsComment", target ="results")
    ResponseWrapperAdsComment toDto(Integer count, List<AdsComment> adsComment);
}