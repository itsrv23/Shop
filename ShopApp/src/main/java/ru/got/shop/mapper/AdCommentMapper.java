package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.AdCommentDto;
import ru.got.shop.model.AdComment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdCommentMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "adId", ignore = true)
    @Mapping(source = "author", target ="userId.id")
    @Mapping(source = "pk", target ="id")
    AdComment toEntity(AdCommentDto adsComment);

    @Mapping(source = "userId.id", target ="author")
    @Mapping(source = "id", target ="pk")
    AdCommentDto toDto(AdComment adComment);

    List<AdCommentDto> toDtos(List<AdComment> adCommentList);
}