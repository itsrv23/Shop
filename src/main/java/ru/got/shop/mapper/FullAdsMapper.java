package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.Ads;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.FullAdDto;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface FullAdsMapper {
    @Mapping(target = "pk", source = "ads.pk")
    @Mapping(target = "authorFirstName", source = "user.firstName")
    @Mapping(target = "authorLastName", source = "user.lastName")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "title", source = "ads.title")
    @Mapping(target = "price", source = "ads.price")
    @Mapping(target = "description", source = "ads.description")
    @Mapping(target = "images", expression = "java(toList(ads))")
    FullAdDto toDto(User user, Ads ads);

    default List<String> toList(Ads ads) {
        String prefix = "/ads/image/";
        return ads != null &&
                ads.getPicture() != null &&
                ads.getPicture().getUuid() != null ? List.of(prefix.concat(ads.getPicture()
                .getUuid()
                .toString())) : new ArrayList<>();
    }
}
