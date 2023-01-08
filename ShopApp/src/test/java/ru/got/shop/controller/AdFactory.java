package ru.got.shop.controller;

import ru.got.shop.dto.AdCreateDto;
import ru.got.shop.dto.AdDto;
import ru.got.shop.model.Picture;

import java.util.List;
import java.util.UUID;

public class AdFactory {
    public static AdDto getAdDto() {
        return AdDto.builder().pk(1).author(1).image(List.of("image")).price(100).title("title").build();
    }

    public static AdCreateDto getCreateAdsDto() {
        return AdCreateDto.builder().title("title").price(100).description("to string").build();
    }

    public static Picture getPicture() {
        Picture picture = new Picture();
        picture.setData(null);
        picture.setUuid(UUID.fromString("eeaffff0-c242-41cb-9baf-44ed689c2c25"));
        picture.setFilePath("src/test/resources/user_avatar_big.jpg");
        picture.setMediaType("image/png");
        picture.setFileSize(8);
        picture.setFileName("test/resources/user_avatar_big.jpg");
        return picture;
    }
}
