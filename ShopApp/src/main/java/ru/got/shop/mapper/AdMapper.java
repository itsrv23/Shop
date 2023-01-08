package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.model.Ad;
import ru.got.shop.model.Picture;
import ru.got.shop.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper {
    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "userId.id")
    @Mapping(target = "image", expression = "java(getLinkList(ad.getPicture()))")
    AdDto toDto(Ad ad);

    List<AdDto> toDtos(List<Ad> adList);

    @Mapping(target = "title", source = "adCreateDto.title")
    @Mapping(target = "price", source = "adCreateDto.price")
    @Mapping(target = "description", source = "adCreateDto.description")
    Ad updateAds(AdCreateDto adCreateDto, Ad ad);

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
    Ad buildAds(User user, AdCreateDto adCreateDto, Picture picture);
}

