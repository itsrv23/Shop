package ru.got.shop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.dto.Ads;
import ru.got.shop.model.dto.ResponseWrapperAds;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseWrapperAdsMapper {

    @Mapping(source = "count", target ="count")
    @Mapping(source = "ads", target ="results")
    ResponseWrapperAds toDto(Integer count, List<Ads> ads);
}
