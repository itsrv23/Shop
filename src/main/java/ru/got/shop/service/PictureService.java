package ru.got.shop.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    byte[] download(MultipartFile file,String uniqueFileName);

    byte[] upload(String uniqueFileName);

    String getUniqueName(String fileName);
}
