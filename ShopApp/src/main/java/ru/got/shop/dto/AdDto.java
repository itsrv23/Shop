package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class AdDto implements Serializable {

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("author")
    private Integer author;

    @JsonProperty("image")
    @Valid
    private List<String> image;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("title")
    private String title;
}

