package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import ru.got.shop.model.FullAds;
import ru.got.shop.model.dto.FullAd;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {
    FullAd toDto(FullAds adsDto);
}
