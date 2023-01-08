package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.FullAdDto;
import ru.got.shop.model.Ad;
import ru.got.shop.model.User;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FullAdMapper {
    @Mapping(target = "pk", source = "ad.id")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "title", source = "ad.title")
    @Mapping(target = "price", source = "ad.price")
    @Mapping(target = "description", source = "ad.description")
    @Mapping(target = "images", expression = "java(toList(ad))")
    FullAdDto toDto(User user, Ad ad);

    default List<String> toList(Ad ad) {
        String prefix = "/ads/image/";
        return ad != null &&
                ad.getPicture() != null &&
                ad.getPicture().getUuid() != null ? List.of(prefix.concat(ad.getPicture()
                .getUuid()
                .toString())) : new ArrayList<>();
    }
}
