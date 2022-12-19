package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PictureService {

    UUID download(MultipartFile file, UUID uuid);

    byte[] upload(UUID uuid);
}
