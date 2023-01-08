package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdCriteriaDto {

    @JsonProperty("description")
    private String description;

    @JsonProperty("min_price")
    private Integer minPrice;

    @JsonProperty("max_price")
    private Integer maxPrice;

    @JsonProperty("title")
    private String title;
}
