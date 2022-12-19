package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.model.Picture;

import java.io.IOException;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PictureMapper {
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "fileSize", source = "file.size")
    @Mapping(target = "mediaType", source = "file.contentType")
    @Mapping(target = "data", source = "file.bytes")
    @Mapping(target = "fileName", source = "file.originalFilename")
    Picture mapToPicture(MultipartFile file, UUID uuid) throws IOException;
}
