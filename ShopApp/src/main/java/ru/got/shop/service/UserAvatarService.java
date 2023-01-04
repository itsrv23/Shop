package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserAvatarService {
    String save(MultipartFile image, String login);
    byte[] download(UUID uuid);

}
