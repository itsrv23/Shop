package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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

  @Length(min = 8)
  @JsonProperty("newPassword")
  private String newPassword;
}

