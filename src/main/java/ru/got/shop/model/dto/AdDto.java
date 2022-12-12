package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Objects;

/**
 * Ads
 */
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.NoArgsConstructor

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
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



  public AdDto pk(Integer pk) {
    this.pk = pk;
    return this;
  }

  /**
   * Get pk
   * @return pk
  */
  
  @Schema(name = "pk", required = false)
  public Integer getPk() {
    return pk;
  }

  public void setPk(Integer pk) {
    this.pk = pk;
  }

  public AdDto author(Integer author) {
    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
  */
  
  @Schema(name = "author", required = false)
  public Integer getAuthor() {
    return author;
  }

  public void setAuthor(Integer author) {
    this.author = author;
  }

  public AdDto title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
  */
  
  @Schema(name = "title", required = false)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AdDto price(Integer price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  
  @Schema(name = "price", required = false)
  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public int hashCode() {
    return Objects.hash(pk, author, title, price);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AdDto adDto = (AdDto) o;
    return Objects.equals(this.pk, adDto.pk) &&
        Objects.equals(this.author, adDto.author) &&
        Objects.equals(this.title, adDto.title) &&
        Objects.equals(this.price, adDto.price);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ads {\n");
    sb.append("    pk: ").append(toIndentedString(pk)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

