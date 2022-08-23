package com.sparta.airbnb_clone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String nickname;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  private boolean valid;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String msg;

  private Boolean isSuperHost;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime createdAt;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private LocalDateTime modifiedAt;
}

