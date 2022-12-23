package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class FullAdDto implements Serializable, FullAd {

    private static final long serialVersionUID = 1L;

    @JsonProperty("pk")
    private Integer pk;

    @JsonProperty("authorFirstName")
    private String authorFirstName;

    @JsonProperty("authorLastName")
    private String authorLastName;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("image")
    private List<String> images;

    @JsonProperty("description")
    private String description;
}
