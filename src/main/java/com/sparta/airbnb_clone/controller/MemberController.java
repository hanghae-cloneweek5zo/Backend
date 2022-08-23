package com.sparta.airbnb_clone.controller;

import com.sparta.airbnb_clone.dto.request.LoginRequestDto;
import com.sparta.airbnb_clone.dto.request.MemberRequestDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  @PostMapping("/isvalidate/email")
  public boolean isvalidateEmail(@RequestBody MemberRequestDto requestDto) {
    return memberService.isValidateEmail(requestDto);
//            ?
//            ResponseDto.success(
//                    MemberResponseDto.builder()
//                            .valid(memberService.isvalidateEmail(requestDto))
//                            .msg("사용할 수 있는 이메일입니다.")
//                            .build()
//            ) :
//            ResponseDto.fail("NICKNAME_DUPLICATED", "중복되는 이메일 입니다.");
//
  }

  @PostMapping("/isvalidate/nickname")
  public boolean validateNickname(@RequestBody MemberRequestDto requestDto) {
    return memberService.isValidateNickname(requestDto);
  }

  @PostMapping("/signup")
  public ResponseDto<?> signup(@RequestBody @Valid MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @PostMapping("/login")
  public ResponseDto<?> login(@RequestBody @Valid LoginRequestDto requestDto,
                              HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

//  @GetMapping("/kakao/callback")
//  public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//    TokenDto tokenDto= memberService.kakaoLogin(code);
//    tokenDto.tokenToHeaders(response);
//    return new ResponseEntity<>(ResponseDto.success(("로그인에 성공하였습니다.")), HttpStatus.OK);
//  }

}
