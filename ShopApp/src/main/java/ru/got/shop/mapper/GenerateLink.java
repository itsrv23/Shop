package ru.got.shop.mapper;

import ru.got.shop.dto.UserDto;
import ru.got.shop.model.User;

import java.util.UUID;

public interface GenerateLink {
    String PATH = "/users/avatar/";

    default String generateLink(User user) {
        if (user.getAvatarId() == null) {
            return null;
        }
        return PATH + user.getAvatarId().getUuid() + "/";
    }

    default UUID getUUID(UserDto userDto) {
        String image = userDto.getImage();
        if (image == null) {
            return null;
        }
        int length = PATH.length();
        return UUID.fromString(image.substring(length, image.length() - 1));
    }
}
