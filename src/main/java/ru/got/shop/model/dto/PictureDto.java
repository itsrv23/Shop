package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PictureDto {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("fileSize")
    private Integer fileSize;
    @JsonProperty("filePath")
    private String filePath;
    @JsonProperty("mediaType")
    private String mediaType;
}
