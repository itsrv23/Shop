package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.AdsComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsCommentMapper {

    @Mapping(source = "author", target ="userId.id")
    AdsComment toEntity(ru.got.shop.model.dto.AdsCommentDto adsComment);

    @Mapping(source = "userId.id", target ="author")
    ru.got.shop.model.dto.AdsCommentDto toDto(AdsComment adsComment);

    List<ru.got.shop.model.dto.AdsCommentDto> toDtos(List<AdsComment> adsCommentList);
}