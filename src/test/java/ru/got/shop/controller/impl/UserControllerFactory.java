package ru.got.shop.controller.impl;

import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;
import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.security.Role;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class UserControllerFactory {
    static final String USER_LOGIN = "user@gmail.com";
    static final String ADMIN_LOGIN = "admin@gmail.com";

    public static byte [] AVATAR_FILE() throws IOException {
        return Files.readAllBytes(Path.of("src/test/resources/user_avatar.jpg"));
    }

    public static byte [] AVATAR_FILE_BIG_FILE() throws IOException {
        return Files.readAllBytes(Path.of("src/test/resources/user_avatar_big.jpg"));
    }

    public static User getUserEntity() throws Exception {
        User user =  new User();
        user.setId(1);
        user.setPhone("+799912345566");
        user.setEmail(USER_LOGIN);
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setPassword("$2a$10$z4L2CvJCdVe2Lfjc1tS5W.WAJ6LECIExfcj.LRhfZy8q.XmaVgorG");
        user.setRoleGroup(Role.USER);
        user.setAvatarId(getAvatarEntity());
        return user;
    }

    public static User getAdminEntity() throws Exception {
        User user =  new User();
        user.setId(2);
        user.setPhone("+799912345566");
        user.setEmail(ADMIN_LOGIN);
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setRoleGroup(Role.ADMIN);
        user.setAvatarId(getAvatarEntity());
        return user;
    }

    public static UserAvatar getAvatarEntity() throws Exception {
        UserAvatar avatar = new UserAvatar();
        User user = new User();
        user.setAvatarId(avatar);
        user.setId(1);
        avatar.setUser(user);
        avatar.setUuid(UUID.fromString("f5845c8a-bbe1-41ca-b654-d14d6a4184cd"));
        avatar.setImage(Base64.getEncoder().encodeToString(AVATAR_FILE()));
        return avatar;
    }

    public static NewPasswordDto getNewPasswordDto(){
        return NewPasswordDto.builder().newPassword("password_new").currentPassword("userpass").build();
    }
}
