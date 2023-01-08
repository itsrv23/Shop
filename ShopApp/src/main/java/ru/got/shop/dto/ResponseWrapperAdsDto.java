package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Builder
@Data
@Valid
public class ResponseWrapperAdsDto {

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<AdDto> results;
}

