package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import ru.got.shop.model.FullAds;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {
    ru.got.shop.openapi.dto.FullAds toDto(FullAds adsDto);
}
