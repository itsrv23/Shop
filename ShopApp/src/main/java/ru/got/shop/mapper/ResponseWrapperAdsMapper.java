package ru.got.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperAdsMapper {

    @Mapping(source = "count", target ="count")
    @Mapping(source = "ads", target ="results")
    ResponseWrapperAdsDto toDto(Integer count, List<AdDto> ads);
}
