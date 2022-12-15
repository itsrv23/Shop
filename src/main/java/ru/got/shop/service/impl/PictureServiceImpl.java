package ru.got.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.service.PictureService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Configuration
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PasswordEncoder passwordEncoder;
    @Value("${picture.dir}")
    private String pictureDir;

    @Override
    public byte[] download(MultipartFile file, String uniqueFileName) {
        Path path = Path.of(pictureDir, uniqueFileName);
        try {
            Files.createDirectories(path.getParent().toAbsolutePath());
            Files.deleteIfExists(path);
            try (InputStream inputStream = file.getInputStream();
                 OutputStream outputStream = Files.newOutputStream(path, CREATE_NEW);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                bufferedInputStream.transferTo(bufferedOutputStream);
                return file.getBytes();
            }
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong during file saving!!!");
        }
    }

    @Override
    public byte[] upload(String uniqueFileName) {
        Path path = Path.of(pictureDir, uniqueFileName);
        return getFileAsByteArray(path);
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
