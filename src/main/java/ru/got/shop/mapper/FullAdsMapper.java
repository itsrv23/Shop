package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import ru.got.shop.model.dto.FullAd;
import ru.got.shop.model.dto.FullAdDto;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {

    FullAdDto toDto(FullAd adsDto);
}
