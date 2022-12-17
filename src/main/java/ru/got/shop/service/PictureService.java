package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface PictureService {

    UUID download(MultipartFile file);

    byte[] upload(UUID uuid);
}
