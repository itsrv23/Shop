package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Objects;

/**
 * FullAds
 */
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.NoArgsConstructor
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class FullAd implements Serializable {

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

    @JsonProperty("description")
    private String description;

    public FullAd pk(Integer pk) {
        this.pk = pk;
        return this;
    }

    /**
     * Get pk
     *
     * @return pk
     */

    @Schema(name = "pk", required = false)
    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public FullAd authorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
        return this;
    }

    /**
     * Get authorFirstName
     *
     * @return authorFirstName
     */

    @Schema(name = "authorFirstName", required = false)
    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public FullAd authorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
        return this;
    }

    /**
     * Get authorLastName
     *
     * @return authorLastName
     */

    @Schema(name = "authorLastName", required = false)
    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public FullAd phone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Get phone
     *
     * @return phone
     */

    @Schema(name = "phone", required = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public FullAd email(String email) {
        this.email = email;
        return this;
    }

    /**
     * Get email
     *
     * @return email
     */

    @Schema(name = "email", required = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FullAd title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get title
     *
     * @return title
     */

    @Schema(name = "title", required = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FullAd price(Integer price) {
        this.price = price;
        return this;
    }

    /**
     * Get price
     *
     * @return price
     */

    @Schema(name = "price", required = false)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public FullAd description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     *
     * @return description
     */

    @Schema(name = "description", required = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FullAd fullAd = (FullAd) o;
        return Objects.equals(this.pk, fullAd.pk) &&
                Objects.equals(this.authorFirstName, fullAd.authorFirstName) &&
                Objects.equals(this.authorLastName, fullAd.authorLastName) &&
                Objects.equals(this.phone, fullAd.phone) &&
                Objects.equals(this.email, fullAd.email) &&
                Objects.equals(this.title, fullAd.title) &&
                Objects.equals(this.price, fullAd.price) &&
                Objects.equals(this.description, fullAd.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, authorFirstName, authorLastName, phone, email, title, price, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class FullAds {\n");
        sb.append("    pk: ").append(toIndentedString(pk)).append("\n");
        sb.append("    authorFirstName: ").append(toIndentedString(authorFirstName)).append("\n");
        sb.append("    authorLastName: ").append(toIndentedString(authorLastName)).append("\n");
        sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
        sb.append("    email: ").append(toIndentedString(email)).append("\n");
        sb.append("    title: ").append(toIndentedString(title)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

