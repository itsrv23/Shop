package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.model.dto.AdDto;

import java.util.UUID;

public interface PictureService {

    UUID download(MultipartFile file, AdDto adDto);

    byte[] upload(Integer ad_pk);

    String getUniqueName(String fileName);
}
