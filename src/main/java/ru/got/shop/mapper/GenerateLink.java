package ru.got.shop.mapper;

import ru.got.shop.model.User;

public interface GenerateLink {
    default String generateLink(User user){
        if (user.getAvatarId() == null) {
            return null;
        }
        return "/users/avatar/" + user.getAvatarId().getUuid() + "/";
    }
}
