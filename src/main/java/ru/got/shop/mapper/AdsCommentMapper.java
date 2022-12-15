package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.AdsComment;
import ru.got.shop.model.dto.AdsCommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsCommentMapper {

    @Mapping(source = "author", target ="userId.id")
    AdsComment toEntity(AdsCommentDto adsComment);

    @Mapping(source = "userId.id", target ="author")
    AdsCommentDto toDto(AdsComment adsComment);

    List<AdsCommentDto> toDtos(List<AdsComment> adsCommentList);
}