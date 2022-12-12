package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.Ads;
import ru.got.shop.model.dto.AdDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "author", target = "userId.id")
    Ads toEntity(AdDto adDto);

    @Mapping(source = "userId.id", target = "author")
    AdDto toDto(Ads ads);

    List<AdDto> toDtos(List<Ads> adsList);
}
