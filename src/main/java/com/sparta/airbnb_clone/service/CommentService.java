package com.sparta.airbnb_clone.service;


import com.sparta.airbnb_clone.dto.request.CommentRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public ResponseDto<?> createComment(CommentRequestDto requestDto, HttpServletRequest httpServlet,Long commentId){
        if(requestDto.getDescription() == null){
            //description
            return ResponseDto.fail("DESCRIPTION_NULL","리뷰를 입력해주세요");
        }
        return ResponseDto.success();
    }
}
