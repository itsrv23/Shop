package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.io.Serializable;


@Builder
@Data
public class LoginReqDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("username")
  @Email
  private String username;

  @JsonProperty("password")
  private String password;
}

