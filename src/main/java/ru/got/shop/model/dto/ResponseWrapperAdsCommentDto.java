package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.io.Serializable;
import javax.validation.Valid;

/**
 * ResponseWrapperAdsCommentDto
 */

@Builder
@Data
@Valid
@AllArgsConstructor
public class ResponseWrapperAdsCommentDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("results")
  @Valid
  private List<AdsCommentDto> results;
}

