package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * NewPassword
 */
@Builder
@Data
public class NewPasswordDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("currentPassword")
  private String currentPassword;

  @Min(8)
  @JsonProperty("newPassword")
  private String newPassword;
}

