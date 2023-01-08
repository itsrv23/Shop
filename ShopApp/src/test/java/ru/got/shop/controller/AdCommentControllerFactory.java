package ru.got.shop.controller;

import ru.got.shop.model.Ad;
import ru.got.shop.model.AdComment;
import ru.got.shop.model.User;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static ru.got.shop.controller.UserControllerFactory.getAdminEntity;
import static ru.got.shop.controller.UserControllerFactory.getUserEntity;

public class AdCommentControllerFactory {

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


    public static Ad getAdminAdsEntity() throws Exception {
        Ad adminAd = new Ad();
        adminAd.setId(ADMIN_ADS_ID);
        adminAd.setUserId(getAdminEntity());
        adminAd.setTitle(VALUE);
        adminAd.setDescription(DESCRIPTION);
        adminAd.setPrice(PRICE);
        adminAd.setAdComment(List.of(getAdminCommentEntity()));
        return adminAd;
    }

    public static Ad getUserAdsEntity() throws Exception {
        Ad userAd = new Ad();
        userAd.setId(USER_ADS_ID);
        userAd.setUserId(getUserEntity());
        userAd.setTitle(VALUE);
        userAd.setDescription(DESCRIPTION);
        userAd.setPrice(PRICE);
        userAd.setAdComment(List.of(getUserCommentEntity()));
        return userAd;
    }

    public static Ad getAnotherUserAdsEntity() throws Exception {
        Ad userAd = new Ad();
        userAd.setId(ANOTHER_USER_ADS_ID);
        userAd.setUserId(getUserEntity());
        userAd.setTitle(ANOTHER_VALUE);
        userAd.setDescription(ANOTHER_DESCRIPTION);
        userAd.setPrice(ANOTHER_PRICE);
        userAd.setAdComment(List.of(getUserCommentEntity()));
        return userAd;
    }

    public static AdComment getAdminCommentEntity() {
        AdComment adminAdComment = new AdComment();
        adminAdComment.setId(ADMIN_ADS_COMMENT_ID);
        User user = new User();
        user.setId(2);
        adminAdComment.setUserId(user);
        adminAdComment.setCreatedAt(OffsetDateTime.of(2022,1,1,0,0,0,0, ZoneOffset.ofHours(0)));
        Ad adminAd = new Ad();
        adminAd.setAdComment(List.of(adminAdComment));
        adminAd.setId(ADMIN_ADS_ID);
        adminAdComment.setAdId(adminAd);
        adminAdComment.setText(TEXT);
        return adminAdComment;
    }

    public static AdComment getUserCommentEntity() {
        AdComment userAdComment = new AdComment();
        userAdComment.setId(USER_ADS_COMMENT_ID);
        User user = new User();
        user.setId(1);
        userAdComment.setUserId(user);
        userAdComment.setCreatedAt(OffsetDateTime.of(2022,1,1,0,0,0,0, ZoneOffset.ofHours(0)));
        Ad userAd = new Ad();
        userAd.setAdComment(List.of(userAdComment));
        userAd.setId(USER_ADS_ID);
        userAdComment.setAdId(userAd);
        userAdComment.setText(TEXT);
        return userAdComment;
    }
}
