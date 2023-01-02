package ru.got.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.AdsCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperAdsCommentMapper {

    @Mapping(source = "count", target ="count")
    @Mapping(source = "adsComment", target ="results")
    ResponseWrapperAdsCommentDto toDto(Integer count, List<AdsCommentDto> adsComment);
}