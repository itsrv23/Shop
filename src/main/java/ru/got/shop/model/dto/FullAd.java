package ru.got.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface FullAd {

    @Schema(name = "pk")
    Integer getPk();

    void setPk(Integer pk);

    @Schema(name = "authorFirstName")
    String getAuthorFirstName();

    void setAuthorFirstName(String authorFirstName);

    @Schema(name = "authorLastName")
    String getAuthorLastName();

    void setAuthorLastName(String authorLastName);

    @Schema(name = "phone")
    String getPhone();

    void setPhone(String phone);

    @Schema(name = "email")
    String getEmail();

    void setEmail(String email);

    @Schema(name = "title")
    String getTitle();

    void setTitle(String title);

    @Schema(name = "price")
    Integer getPrice();

    void setPrice(Integer price);

    @Schema(name = "description")
    String getDescription();

    void setDescription(String description);
}
