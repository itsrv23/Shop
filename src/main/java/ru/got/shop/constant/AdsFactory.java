package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.openapi.dto.*;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdsFactory {
    private static final String IMAGE = "/path/to/image.jpg";
    private static final String PHONE = "+79223344554";
    private static final String VALUE = "someStringValue";
    private static final String MAIL = "someEmail@mail.com";
    private static final Integer PRICE = 1500;

    public static ResponseWrapperAdsComment getResponseWrapperAdsComment() {
        return ResponseWrapperAdsComment.builder()
                .count(2)
                .results(List.of(getAdsComment(), getAdsComment()))
                .build();
    }

    public static ResponseWrapperAds getResponseWrapperAds() {
        return ResponseWrapperAds.builder()
                .count(2)
                .results(List.of(getAds(), getAds()))
                .build();
    }

    public static FullAds getFullAds() {
        return FullAds.builder()
                .authorFirstName(VALUE)
                .authorLastName(VALUE)
                .description(VALUE)
                .email(MAIL)
                .image(IMAGE)
                .phone(PHONE)
                .pk(1)
                .price(PRICE)
                .title(VALUE)
                .build();
    }

    public static Ads getAds() {
        return Ads.builder()
                .author(1)
                .image(IMAGE)
                .pk(1)
                .price(PRICE)
                .title(VALUE)
                .build();
    }

    public static AdsComment getAdsComment() {
        return AdsComment.builder()
                .author(1)
                .createdAt(OffsetDateTime.now())
                .pk(1)
                .text(VALUE)
                .build();
    }

    public static CreateAds getCreateAds() {
        return CreateAds.builder()
                .description(VALUE)
                .image(IMAGE)
                .pk(1)
                .price(PRICE)
                .title(VALUE)
                .build();
    }
}
