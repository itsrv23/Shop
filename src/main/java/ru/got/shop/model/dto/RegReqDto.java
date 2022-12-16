package ru.got.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.Serializable;

/**
 * RegReq
 */
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.NoArgsConstructor
@Data
@Valid
public class RegReqDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Email
  @JsonProperty("username")
  private String username;

  @Length(min = 8)
  @JsonProperty("password")
  private String password;

  private String firstName;
  private String lastName;
  private String phone;

}

