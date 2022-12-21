package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.Ads;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.AdCreateDto;
import ru.got.shop.model.dto.AdDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "userId.id")
    @Mapping(target = "image", expression = "java(getLinkList(ads.getPicture()))")
    AdDto toDto(Ads ads);

    List<AdDto> toDtos(List<Ads> adsList);

    @Mapping(target = "title", source = "adCreateDto.title")
    @Mapping(target = "price", source = "adCreateDto.price")
    @Mapping(target = "description", source = "adCreateDto.description")
    Ads updateAds(AdCreateDto adCreateDto, Ads ads);

    default List<String> getLinkList(Picture picture) {
        String prefix = "/ads/image/";
        return picture != null ? List.of(prefix.concat(picture.getUuid().toString())) : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userId.id", source = "user.id")
    @Mapping(target = "picture", source = "picture")
    @Mapping(target = "description", source = "adCreateDto.description")
    @Mapping(target = "price", source = "adCreateDto.price")
    @Mapping(target = "title", source = "adCreateDto.title")
    Ads buildAds(User user, AdCreateDto adCreateDto, Picture picture);
}

