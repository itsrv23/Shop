package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.mapper.PictureMapper;
import ru.got.shop.model.Picture;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    @Override
    public UUID download(MultipartFile file, UUID uuid) {
        Picture picture;
        try {
            picture = pictureMapper.mapToPicture(file, uuid);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong while picture reading!!!", e);
        }
        return pictureRepository.save(picture).getUuid();
    }

    @Override
    public byte[] upload(UUID uuid) {
        Picture picture = pictureRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid.toString().concat(" picture not found")));
        return picture.getData();
    }
}
