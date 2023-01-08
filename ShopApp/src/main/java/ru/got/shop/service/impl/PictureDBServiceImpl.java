package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.got.shop.exception.PictureNotFoundException;
import ru.got.shop.model.Picture;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureDBServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    @Override
    public Picture download(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public void update(UUID uuid, Picture picture) {
        Picture picture1 = pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
        picture.setUuid(picture1.getUuid());
        pictureRepository.save(picture);
    }

    @Override
    public byte[] upload(UUID uuid) {
        Picture picture = pictureRepository.findByUuid(uuid).orElseThrow(() -> new PictureNotFoundException(uuid));
        return picture.getData();
    }
}
