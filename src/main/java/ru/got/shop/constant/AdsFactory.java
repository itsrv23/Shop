package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.model.dto.*;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AdsFactory {
    private static final Integer IMAGE_ID = 4;
    private static final String PHONE = "+79223344554";
    private static final String VALUE = "someStringValue";
    private static final String MAIL = "someEmail@mail.com";
    private static final Integer PRICE = 1500;

    public static ResponseWrapperAdsCommentDto getResponseWrapperAdsComment() {
        return ResponseWrapperAdsCommentDto.builder()
                .count(2)
                .results(List.of(getAdsComment(), getAdsComment()))
                .build();
    }

    public static ResponseWrapperAdsDto getResponseWrapperAds() {
        return ResponseWrapperAdsDto.builder().count(2).results(List.of(getAds(), getAds())).build();
    }

    public static FullAdDto getFullAds() {
        return FullAdDto.builder()
                .authorFirstName(VALUE)
                .authorLastName(VALUE)
                .description(VALUE)
                .email(MAIL)
                .phone(PHONE)
                .pk(1)
                .price(PRICE)
                .title(VALUE)
                .build();
    }

    public static AdDto getAds() {
        return AdDto.builder().author(1).pk(1).price(PRICE).title(VALUE).build();
    }

    public static AdsCommentDto getAdsComment() {
        return AdsCommentDto.builder().author(1).createdAt(OffsetDateTime.now()).pk(1).text(VALUE).build();
    }
//    public static CreateAds getCreateAds() {
//        return CreateAds.builder()
//                .description(VALUE)
//                .ima(IMAGE_IDS)
//                .pk(1)
//                .price(PRICE)
//                .title(VALUE)
//                .build();
//    }
}
