package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {
    private static final String FIRSTNAME = "Игорь";
    private static final String LASTNAME = "Шапранский";
    private static final String EMAIL = "is@srub.com";
    private static final String PHONE = "+7(777)777-77-77";
    private static final Integer ID = 777;

    private static final String FIRSTNAME1 = "Алексей";
    private static final String LASTNAME1 = "Виноградов";
    private static final String EMAIL1 = "av@srub.com";
    private static final String PHONE1 = "+7(777)111-11-11";
    private static final Integer ID1 = 111;

    private static final String FIRSTNAME2 = "Александра";
    private static final String LASTNAME2 = "Пивоварова";
    private static final String EMAIL2 = "ap@srub.com";
    private static final String PHONE2 = "+7(222)222-22-22";
    private static final Integer ID2 = 222;

    private static final String OLDPASSWORD = "СтарыйПароль";
    private static final String NEWPASSWORD = "НовыйПароль";

    private static final User USER = User.builder()
            .firstName(FIRSTNAME)
            .lastName(LASTNAME)
            .email(EMAIL)
            .phone(PHONE)
            .id(ID)
            .build();

    private static final User USER1 = User.builder()
            .firstName(FIRSTNAME1)
            .lastName(LASTNAME1)
            .email(EMAIL1)
            .phone(PHONE1)
            .id(ID1)
            .build();

    private static final User USER2 = User.builder()
            .firstName(FIRSTNAME2)
            .lastName(LASTNAME2)
            .email(EMAIL2)
            .phone(PHONE2)
            .id(ID2)
            .build();

    public static User getUser() {
        return USER;
    }

    public static ResponseWrapperUser getResponseWrapperUser() {
        return ResponseWrapperUser.builder()
                .count(3)
                .results(List.of(USER, USER1, USER2))
                .build();
    }

    public static NewPassword getNewPassword() {
        return NewPassword.builder()
                .currentPassword(OLDPASSWORD)
                .newPassword(NEWPASSWORD)
                .build();
    }
}