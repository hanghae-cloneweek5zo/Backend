package com.sparta.airbnb_clone.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentId;

    private Boolean isLike;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postLikeCnt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long commentLikeCnt;

    private String msg;
}
