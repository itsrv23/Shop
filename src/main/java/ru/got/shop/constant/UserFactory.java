package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {
    private static final String FIRSTNAME1 = "Игорь";
    private static final String LASTNAME1 = "Шапранский";
    private static final String EMAIL1 = "is@srub.com";
    private static final String PHONE1 = "+7(777)777-77-77";
    private static final Integer ID1 = 1;

    private static final String FIRSTNAME2 = "Алексей";
    private static final String LASTNAME2 = "Виноградов";
    private static final String EMAIL2 = "av@srub.com";
    private static final String PHONE2 = "+7(777)111-11-11";
    private static final Integer ID2 = 2;

    private static final String FIRSTNAME3 = "Александра";
    private static final String LASTNAME3 = "Пивоварова";
    private static final String EMAIL3 = "ap@srub.com";
    private static final String PHONE3 = "+7(222)222-22-22";
    private static final Integer ID3 = 3;

    private static final String OLDPASSWORD = "СтарыйПароль";
    private static final String NEWPASSWORD = "НовыйПароль";

    private static final User USER1 = User.builder()
            .firstName(FIRSTNAME1)
            .lastName(LASTNAME1)
            .email(EMAIL1)
            .phone(PHONE1)
            .id(ID1)
            .build();

    private static final User USER2 = User.builder()
            .firstName(FIRSTNAME1)
            .lastName(LASTNAME1)
            .email(EMAIL1)
            .phone(PHONE1)
            .id(ID1)
            .build();

    private static final User USER3 = User.builder()
            .firstName(FIRSTNAME2)
            .lastName(LASTNAME2)
            .email(EMAIL2)
            .phone(PHONE2)
            .id(ID2)
            .build();

    public static User getUser() {
        return USER1;
    }

    public static ResponseWrapperUser getResponseWrapperUser() {
        return ResponseWrapperUser.builder()
                .count(3)
                .results(List.of(USER1, USER2, USER3))
                .build();
    }

    public static NewPassword getNewPassword() {
        return NewPassword.builder()
                .currentPassword(OLDPASSWORD)
                .newPassword(NEWPASSWORD)
                .build();
    }
}