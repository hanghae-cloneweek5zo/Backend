//package com.sparta.airbnb_clone.controller;
//
//import com.sparta.woonha99.dto.response.ResponseDto;
//import com.sparta.woonha99.service.PostLikeService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequiredArgsConstructor
//public class PostLikeController {
//
//    private final PostLikeService postLikeService;
//
//    @GetMapping("/auth/posts/{postId}/likes")
//    public ResponseDto<?> togglePostLike(@PathVariable Long postId, HttpServletRequest request) {
//        return postLikeService.togglePostLikeByPostId(postId, request);
//    }
//
//}
