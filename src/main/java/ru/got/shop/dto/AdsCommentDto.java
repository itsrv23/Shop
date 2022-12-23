package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Objects;

/**
 * AdsComment
 */

@Builder
@Data
public class AdsCommentDto implements Serializable {

  @JsonProperty("pk")
  private Integer pk;

  @JsonProperty("author")
  private Integer author;

  @JsonProperty("createdAt")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @JsonProperty("text")
  private String text;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AdsCommentDto that = (AdsCommentDto) o;
    return pk.equals(that.pk);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pk);
  }
}

