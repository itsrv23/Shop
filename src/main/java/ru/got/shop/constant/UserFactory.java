package ru.got.shop.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserFactory {
    private static final String FIRSTNAME1 = "Игорь";
    private static final String LASTNAME1 = "Шапранский";
    private static final String EMAIL1 = "is@srub.com";
    private static final String PHONE1 = " ";
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

    private static final UserDto USER_DTO_1 = UserDto.builder()
            .firstName(FIRSTNAME1)
            .lastName(LASTNAME1)
            .email(EMAIL1)
            .phone(PHONE1)
            .id(ID1)
            .build();

    private static final UserDto USER_DTO_2 = UserDto.builder()
            .firstName(FIRSTNAME1)
            .lastName(LASTNAME1)
            .email(EMAIL1)
            .phone(PHONE1)
            .id(ID1)
            .build();

    private static final UserDto USER_DTO_3 = UserDto.builder()
            .firstName(FIRSTNAME2)
            .lastName(LASTNAME2)
            .email(EMAIL2)
            .phone(PHONE2)
            .id(ID2)
            .build();

    public static UserDto getUser() {
        return USER_DTO_1;
    }

    public static ResponseWrapperUserDto getResponseWrapperUser() {
        return ResponseWrapperUserDto.builder()
                .count(3)
                .results(List.of(USER_DTO_1, USER_DTO_2, USER_DTO_3))
                .build();
    }

    public static NewPasswordDto getNewPassword() {
        return NewPasswordDto.builder()
                .currentPassword(OLDPASSWORD)
                .newPassword(NEWPASSWORD)
                .build();
    }
}