package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.got.shop.model.Picture;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    @Override
    public Picture download(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Override
    public void update(UUID uuid, Picture picture) {
        pictureRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid.toString().concat(" Picture does noe " + "exist")));
        picture.setUuid(uuid);
        pictureRepository.save(picture);
    }

    @Override
    public byte[] upload(UUID uuid) {
        Picture picture = pictureRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid.toString().concat(" picture not found")));
        return picture.getData();
    }
}
