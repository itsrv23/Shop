package ru.got.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.Serializable;


@Builder
@Data
@Valid
public class CreateUserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("firstName")
  private String firstName;

  @JsonProperty("lastName")
  private String lastName;

  @JsonProperty("password")
  private String password;

  @JsonProperty("phone")
  private String phone;

  @Email
  @JsonProperty("email")
  private String email;

}

