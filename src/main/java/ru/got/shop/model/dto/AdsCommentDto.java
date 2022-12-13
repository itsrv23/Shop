package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * AdsComment
 */

@Builder
@Data
public class AdsCommentDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("pk")
  private Integer pk;

  @JsonProperty("author")
  private Integer author;

  @JsonProperty("createdAt")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @JsonProperty("text")
  private String text;
}

