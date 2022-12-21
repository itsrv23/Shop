package ru.got.shop.service;

import ru.got.shop.model.Picture;

import java.util.UUID;

public interface PictureService {

    Picture download(Picture picture);

    void update(UUID uuid, Picture picture);

    byte[] upload(UUID uuid);
}
