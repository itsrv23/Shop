package ru.got.shop.controller;

import ru.got.shop.dto.AdDto;
import ru.got.shop.dto.ResponseWrapperAdsDto;
import ru.got.shop.model.Picture;

import java.util.List;
import java.util.UUID;

public class AdsFactory {
    public static AdDto getAdDto() {
        return AdDto.builder().pk(1).author(1).image(List.of("image")).price(100).title("title").build();
    }

    ResponseWrapperAdsDto getWrapperAdsDto() {
        return ResponseWrapperAdsDto.builder().count(1).results(List.of(getAdDto())).build();
    }

    public static Picture getPicture() {
        Picture picture = new Picture();
        picture.setData(null);
        picture.setUuid(UUID.fromString("eeaffff0-c242-41cb-9baf-44ed689c2c25"));
        picture.setFilePath("src/test/resources/user_avatar_big.jpg");
        picture.setMediaType("image/png");
        picture.setFileSize(8);
        picture.setFileName("user_avatar_big.jpg");
        return picture;
    }
}
