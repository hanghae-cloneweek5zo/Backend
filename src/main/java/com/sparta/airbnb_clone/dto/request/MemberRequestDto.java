package com.sparta.airbnb_clone.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

  @NotBlank
  private String email;

  @NotBlank
  private String nickname;

  @NotBlank
  private String password;

  @NotBlank
  private String passwordConfirm;
}
