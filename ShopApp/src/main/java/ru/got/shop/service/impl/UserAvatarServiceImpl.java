package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.exception.AvatarNotFoundException;
import ru.got.shop.exception.CustomIOException;
import ru.got.shop.exception.UserNotFoundException;
import ru.got.shop.model.User;
import ru.got.shop.model.UserAvatar;
import ru.got.shop.repository.UserAvatarRepository;
import ru.got.shop.repository.UserRepository;
import ru.got.shop.service.UserAvatarService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAvatarServiceImpl implements UserAvatarService {
    private final UserAvatarRepository userAvatarRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public String save(MultipartFile image, String login) {
        String encodedString;
        User user = userRepository.findFirstByEmail(login).orElseThrow(() -> new UserNotFoundException(login));
        UserAvatar avatar = userAvatarRepository.findFirstByUser(user).orElseGet(UserAvatar::new);
        try {
            byte[] fileContent = image.getBytes();
            encodedString = Base64.getEncoder().encodeToString(fileContent);
            avatar.setUser(user);
            avatar.setImage(encodedString);
            userAvatarRepository.save(avatar);
            user.setAvatarId(avatar);
            userRepository.save(user);
            return avatar.getUuid().toString();
        } catch (IOException e) {
            log.warn("Error uploading an avatar from user :: {}", user.getEmail());
            throw new CustomIOException();
        }
    }

    @Override
    public byte[] download(UUID uuid) {
        UserAvatar avatar = userAvatarRepository.findById(uuid).orElseThrow(() -> new AvatarNotFoundException(uuid));
        return Base64.getDecoder().decode(avatar.getImage());
    }
}
