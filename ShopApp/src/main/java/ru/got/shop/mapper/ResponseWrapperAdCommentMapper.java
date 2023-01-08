package ru.got.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.dto.ResponseWrapperAdsCommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperAdCommentMapper {

    @Mapping(source = "count", target ="count")
    @Mapping(source = "adsComment", target ="results")
    ResponseWrapperAdsCommentDto toDto(Integer count, List<AdCommentDto> adsComment);
}