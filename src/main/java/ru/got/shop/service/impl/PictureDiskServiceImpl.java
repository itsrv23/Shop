package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import ru.got.shop.model.Picture;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PictureDiskServiceImpl implements PictureService {
    @Value("${picture.dir}")
    private String dir;
    private final PictureRepository pictureRepository;

    @Override
    public Picture download(Picture picture) {
        Path path = Path.of(dir, getFileNane(picture));
        picture.setFilePath(path.toAbsolutePath().toString());
        saveToDisk(path, picture.getData());
        picture.setData(null);
        return pictureRepository.save(picture);
    }

    @Override
    public void update(UUID uuid, Picture picture) {
        Picture pictureToSave = pictureRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid + " Picture doesn't exist."));
        Path path = Path.of(dir, getFileNane(picture));
        pictureToSave.setFilePath(path.toAbsolutePath().toString());
        saveToDisk(path, picture.getData());
        pictureRepository.save(pictureToSave);
    }

    @Override
    @SneakyThrows
    public byte[] upload(UUID uuid) {
        Picture picture = pictureRepository.findByUuid(uuid)
                .orElseThrow(() -> new EntityNotFoundException(uuid + " Picture doesn't exist."));
        Path path = Paths.get(picture.getFilePath());
        return Files.readAllBytes(path);
    }

    @SneakyThrows
    private void saveToDisk(Path path, byte[] bytes) {
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        Files.write(path, bytes);
    }

    private String getFileNane(Picture picture) {
        String mdSum = DigestUtils.md5DigestAsHex(picture.getData());
        return String.format("%s%s", mdSum, picture.getFileName().substring(picture.getFileName().lastIndexOf(".")));
    }
}
