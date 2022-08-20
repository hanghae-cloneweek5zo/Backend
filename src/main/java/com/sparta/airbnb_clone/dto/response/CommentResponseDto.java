package com.sparta.airbnb_clone.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
    private Long commentId;
    private String description;
    private int star;
}