package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * ResponseWrapperUser
 */

@Builder
@Data
@Valid
@AllArgsConstructor
public class ResponseWrapperUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("results")
    private List<UserDto> results;

}

