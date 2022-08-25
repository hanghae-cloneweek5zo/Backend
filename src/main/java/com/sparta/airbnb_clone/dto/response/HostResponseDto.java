package com.sparta.airbnb_clone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HostResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImgUrl;
    private int reviewCnt;
    private Boolean isSuperHost;
    private LocalDateTime createdAt;
}
