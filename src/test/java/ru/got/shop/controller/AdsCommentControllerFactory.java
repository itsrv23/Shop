package ru.got.shop.controller;

import ru.got.shop.model.Ads;
import ru.got.shop.model.AdsComment;
import ru.got.shop.model.User;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.got.shop.controller.UserControllerFactory.*;

public class AdsCommentControllerFactory {

    static final Integer PRICE = 2500;

    static final Integer ADMIN_ADS_ID = 2;

    static final Integer USER_ADS_ID = 1;

    static final Integer ADMIN_ADS_COMMENT_ID = 2;

    static final Integer USER_ADS_COMMENT_ID = 1;

    static final String VALUE = "Переноска для кошки";

    static final String DESCRIPTION = "В хорошем состоянии.";

    static final String TEXT = "Подойдет для самолета?";


    static final Integer ANOTHER_USER_ADS_ID = 3;
    static final Integer ANOTHER_PRICE = 7500;
    static final String ANOTHER_VALUE = "Вольер для щенков. ";
    static final String ANOTHER_DESCRIPTION = "1м * 1м * 1м, с дверцей";


    public static Ads getAdminAdsEntity() throws Exception {
        Ads adminAds = new Ads();
        adminAds.setId(ADMIN_ADS_ID);
        adminAds.setUserId(getAdminEntity());
        adminAds.setTitle(VALUE);
        adminAds.setDescription(DESCRIPTION);
        adminAds.setPrice(PRICE);
        adminAds.setAdsComment(List.of(getAdminCommentEntity()));
        return adminAds;
    }

    public static Ads getUserAdsEntity() throws Exception {
        Ads userAds = new Ads();
        userAds.setId(USER_ADS_ID);
        userAds.setUserId(getUserEntity());
        userAds.setTitle(VALUE);
        userAds.setDescription(DESCRIPTION);
        userAds.setPrice(PRICE);
        userAds.setAdsComment(List.of(getUserCommentEntity()));
        return userAds;
    }

    public static Ads getAnotherUserAdsEntity() throws Exception {
        Ads userAds = new Ads();
        userAds.setId(ANOTHER_USER_ADS_ID);
        userAds.setUserId(getUserEntity());
        userAds.setTitle(ANOTHER_VALUE);
        userAds.setDescription(ANOTHER_DESCRIPTION);
        userAds.setPrice(ANOTHER_PRICE);
        userAds.setAdsComment(List.of(getUserCommentEntity()));
        return userAds;
    }

    public static AdsComment getAdminCommentEntity() {
        AdsComment adminAdsComment = new AdsComment();
        adminAdsComment.setId(ADMIN_ADS_COMMENT_ID);
        User user = new User();
        user.setId(2);
        adminAdsComment.setUserId(user);
        adminAdsComment.setCreatedAt(OffsetDateTime.of(2022,1,1,0,0,0,0, ZoneOffset.ofHours(0)));
        Ads adminAds = new Ads();
        adminAds.setAdsComment(List.of(adminAdsComment));
        adminAds.setId(ADMIN_ADS_ID);
        adminAdsComment.setAdsId(adminAds);
        adminAdsComment.setText(TEXT);
        return adminAdsComment;
    }

    public static AdsComment getUserCommentEntity() {
        AdsComment userAdsComment = new AdsComment();
        userAdsComment.setId(USER_ADS_COMMENT_ID);
        User user = new User();
        user.setId(1);
        userAdsComment.setUserId(user);
        userAdsComment.setCreatedAt(OffsetDateTime.of(2022,1,1,0,0,0,0, ZoneOffset.ofHours(0)));
        Ads userAds = new Ads();
        userAds.setAdsComment(List.of(userAdsComment));
        userAds.setId(USER_ADS_ID);
        userAdsComment.setAdsId(userAds);
        userAdsComment.setText(TEXT);
        return userAdsComment;
    }
}
