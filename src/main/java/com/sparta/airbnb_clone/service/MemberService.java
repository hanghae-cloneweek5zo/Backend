package com.sparta.airbnb_clone.service;

import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.dto.request.LoginRequestDto;
import com.sparta.airbnb_clone.dto.request.MemberRequestDto;
import com.sparta.airbnb_clone.dto.request.TokenDto;
import com.sparta.airbnb_clone.dto.response.MemberResponseDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (!isvalidateEmail(requestDto)) {
        return ResponseDto.fail("Email_DUPLICATED", "중복된 이메일 입니다.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    Member member = Member.builder()
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())     //빠져있어서 회원가입 못했었어ㅠㅠ
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .build();
    memberRepository.save(member);

    return ResponseDto.success(
            MemberResponseDto.builder()
                    .email(member.getEmail())
                    .msg("회원가입 완료")
                    .build()
    );
  }

  @Transactional
  public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
    Member member = isPresentEmail(requestDto.getEmail());
    if (null == member) {
      return ResponseDto.fail("EMAIL_NOT_FOUND",
          "사용자를 찾을 수 없습니다.");
    }

    if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
      return ResponseDto.fail("INVALID_PASSWORD", "비밀번호가 일치하지 않습니다.");
    }

    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

    return ResponseDto.success(
            MemberResponseDto.builder()
                    .email(member.getEmail())
                    .msg("로그인 성공")
                    .build()
    );
  }

//  @Transactional
//  public ResponseDto<?> logout(HttpServletRequest request) {
//    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//    Member member = tokenProvider.getMemberFromAuthentication();
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//          "사용자를 찾을 수 없습니다.");
//    }
//    return tokenProvider.deleteRefreshToken(member);
////  }

  //  @Transactional
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//    Member member = tokenProvider.getMemberFromAuthentication();
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//          "사용자를 찾을 수 없습니다.");
//    }
//
//    Authentication authentication = tokenProvider.getAuthentication(request.getHeader("Access-Token"));
//    RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);
//
//    if (!refreshToken.getValue().equals(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//
//    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
//    refreshToken.updateValue(tokenDto.getRefreshToken());
//    tokenToHeaders(tokenDto, response);
//    return ResponseDto.success("success");
//  }



  public boolean isvalidateEmail(MemberRequestDto requestDto) {
    return isPresentEmail(requestDto.getEmail()) == null ? true : false;
  }

  public boolean isvalidateNickname(MemberRequestDto requestDto) {
      return isPresentNickname(requestDto.getNickname()) == null ? true : false;
  }

  @Transactional(readOnly = true)
  public Member isPresentNickname(String nickname) {
    Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
    return optionalMember.orElse(null);
  }

  @Transactional(readOnly = true)
  public Member isPresentEmail(String email) {
    Optional<Member> optionalMember = memberRepository.findByEmail(email);
    return optionalMember.orElse(null);
  }

  public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
    response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
    response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
//    response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
  }

}
