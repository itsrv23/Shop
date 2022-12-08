package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {
    private static final String FIRSTNAME = "Андрей";
    private static final String LASTNAME = "Миронов";
    private static final String EMAIL = "andrew@mironov.com";
    private static final String PHONE = "+7(777)777-77-77";
    private static final Integer ID = 777;

    private static final String FIRSTNAME1 = "Алексей";
    private static final String LASTNAME1 = "Виноградов";
    private static final String EMAIL1 = "av@mironov.com";
    private static final String PHONE1 = "+7(777)111-11-11";
    private static final Integer ID1 = 111;

    private static final String FIRSTNAME2 = "Александра";
    private static final String LASTNAME2 = "Пивоварова";
    private static final String EMAIL2 = "ap@mironov.com";
    private static final String PHONE2 = "+7(222)222-22-22";
    private static final Integer ID2 = 222;

    private static final String OLDPASSWORD = "СтарыйПароль";
    private static final String NEWPASSWORD = "НовыйПароль";

    public static User getUser() {
        return User.builder()
                .firstName(FIRSTNAME)
                .lastName(LASTNAME)
                .email(EMAIL)
                .phone(PHONE)
                .id(ID)
                .build();
    }

    public static ResponseWrapperUser getResponseWrapperUser() {
        return ResponseWrapperUser.builder()
                .count(3)
                .results(List.of(new User(EMAIL1, FIRSTNAME1, ID1, LASTNAME1, PHONE1),
                        new User(EMAIL2, FIRSTNAME2, ID2, LASTNAME2, PHONE2),
                        getUser()))
                .build();
    }

    public static NewPassword getNewPassword() {
        return NewPassword.builder()
                .currentPassword(OLDPASSWORD)
                .newPassword(NEWPASSWORD)
                .build();
    }
}