package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class AdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("author")
    private Integer author;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("description")
    private String description;
}

