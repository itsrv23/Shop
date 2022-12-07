package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.Ads;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "author", target ="userId.id")
    Ads toEntity(ru.got.shop.openapi.dto.Ads ads);

    @Mapping(source = "userId.id", target ="author")
    ru.got.shop.openapi.dto.Ads toDto(Ads ads);

    List<ru.got.shop.openapi.dto.Ads> toDtos(List<Ads> adsList);
}
