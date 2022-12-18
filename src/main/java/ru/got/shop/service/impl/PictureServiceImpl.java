package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.mapper.PictureMapper;
import ru.got.shop.model.Picture;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
//@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PasswordEncoder passwordEncoder;
    private final PictureRepository pictureRepository;
    private final PictureMapper pictureMapper;

    @Value("${picture.dir}")
    private String pictureDir;

    @Override
    public UUID download(MultipartFile file) {
//        Path path = Path.of(pictureDir, uniqueFileName);
//        try {
//            Files.createDirectories(path.getParent().toAbsolutePath());
//            Files.deleteIfExists(path);
//            try (InputStream inputStream = file.getInputStream();
//                 OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW);
//                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
//                bufferedInputStream.transferTo(bufferedOutputStream);
//                return file.getBytes();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Something went wrong during file saving!!!");
//        }
        Picture picture;
        try {
            picture = pictureMapper.mapToPicture(file);
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

    private byte[] getFileAsByteArray(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during file reading", e);
        }
    }
}
