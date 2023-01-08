package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Builder
@Data
@Valid
@AllArgsConstructor
public class ResponseWrapperUserDto {

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<UserDto> results;
}

