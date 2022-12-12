package ru.got.shop.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public interface FullAd {

    @Schema(name = "pk", required = false)
    Integer getPk();

    void setPk(Integer pk);

    @Schema(name = "authorFirstName", required = false)
    String getAuthorFirstName();

    void setAuthorFirstName(String authorFirstName);

    @Schema(name = "authorLastName", required = false)
    String getAuthorLastName();

    void setAuthorLastName(String authorLastName);

    @Schema(name = "phone", required = false)
    String getPhone();

    void setPhone(String phone);

    @Schema(name = "email", required = false)
    String getEmail();

    void setEmail(String email);

    @Schema(name = "title", required = false)
    String getTitle();

    void setTitle(String title);

    @Schema(name = "price", required = false)
    Integer getPrice();

    void setPrice(Integer price);

    @Schema(name = "description", required = false)
    String getDescription();

    void setDescription(String description);
}
