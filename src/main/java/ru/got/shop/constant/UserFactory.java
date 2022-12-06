package ru.got.shop.constant;

import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

import java.util.List;

public final class UserFactory {

    public static User getUser () {
        return User.builder()
                .firstName("Андрей")
                .lastName("Миронов")
                .email("andrew@mironov.com")
                .phone("+7(777)777-77-77")
                .id(777)
                .build();
    }

    public static ResponseWrapperUser getResponseWrapperUser () {
        return ResponseWrapperUser.builder()
                .count(3)
                .results(List.of(new User("av@mironov.com", "Алексей", 1, "Виноградов", "+7(777)111-11-11"),
                        new User("ap@mironov.com", "Александра", 1, "Пивоварова", "+7(777)111-11-11"),
                        getUser()))
                .build();
    }

    public static NewPassword getNewPassword () {
        return NewPassword.builder()
                .currentPassword("СтарыйПароль")
                .newPassword("НовыйПароль")
                .build();
    }
}
