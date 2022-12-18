package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.dto.AdDto;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(target = "picture", ignore = true)
    @Mapping(target = "picture.uuid", expression = "java(getPicUUID(adDto))")
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "adsComment", ignore = true)
    @Mapping(target = "userId.id", source = "author")
    Ads toEntity(AdDto adDto);

    @Mapping(target = "author", source = "userId.id")
    @Mapping(target = "image", expression = "java(getLink(ads.getPicture()))")
    AdDto toDto(Ads ads);

    List<AdDto> toDtos(List<Ads> adsList);

    default String getLink(Picture picture) {
        return picture != null ? "/ads/image/".concat(picture.getUuid().toString()) : null;
    }

    default UUID getPicUUID(AdDto adDto) {
        return adDto.getImage() != null ? UUID.fromString(adDto.getImage()) : null;
    }
}

