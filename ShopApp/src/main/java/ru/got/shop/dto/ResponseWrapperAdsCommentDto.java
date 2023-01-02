package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

/**
 * ResponseWrapperAdsCommentDto
 */

@Builder
@Data
@Valid
@AllArgsConstructor
public class ResponseWrapperAdsCommentDto implements Serializable {

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("results")
  @Valid
  private List<AdsCommentDto> results;
}

