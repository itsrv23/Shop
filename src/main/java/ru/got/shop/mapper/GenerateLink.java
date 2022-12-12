package ru.got.shop.mapper;

import ru.got.shop.model.User;

public interface GenerateLink {
    default String generateLink(User user){
        if (user.getAvatarId() == null) {
            return null;
        }
        return "http://127.0.0.1:8080/images/" + user.getAvatarId().getUuid() + "/";
    }
}
