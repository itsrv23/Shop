package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.model.Picture;
import ru.got.shop.model.dto.AdDto;
import ru.got.shop.repository.PictureRepository;
import ru.got.shop.service.PictureService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PasswordEncoder passwordEncoder;

    private final PictureRepository pictureRepository;

    @Value("${picture.dir}")
    private String pictureDir;

    @Override
    public UUID download(MultipartFile file, AdDto adDto) {
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
        Picture picture = new Picture();
        picture.setFileSize(file.getSize());
        picture.setFilePath(adDto.getImage());
        picture.setMediaType(file.getContentType());
        picture.setAdsId(adDto.getPk());
        try {
            picture.setData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pictureRepository.save(picture).getUuid();
    }

    @Override
    public byte[] upload(Integer ad_pk) {
//        Path path = Path.of(pictureDir, uniqueFileName);
        Picture picture = pictureRepository.findByAdsId(ad_pk)
                .orElseThrow(() -> new EntityNotFoundException("picture not found"));
        return picture.getData();
    }

    @Override
    public String getUniqueName(String fileName) {
        fileName = passwordEncoder.encode(fileName) +
                Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        return fileName;
    }

    private byte[] getFileAsByteArray(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during file reading", e);
        }
    }
}
