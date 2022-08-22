package com.sparta.airbnb_clone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.airbnb_clone.domain.Member;
import com.sparta.airbnb_clone.domain.UserDetailsImpl;
import com.sparta.airbnb_clone.dto.request.KakaoUserInfoDto;
import com.sparta.airbnb_clone.dto.request.LoginRequestDto;
import com.sparta.airbnb_clone.dto.request.MemberRequestDto;
import com.sparta.airbnb_clone.dto.request.TokenDto;
import com.sparta.airbnb_clone.dto.response.MemberResponseDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (!isValidateEmail(requestDto)) {
        return ResponseDto.fail("Email_DUPLICATED", "중복된 이메일 입니다.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    Member member = Member.builder()
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .build();
    memberRepository.save(member);

    return ResponseDto.success(
            MemberResponseDto.builder()
                    .nickname(member.getNickname())
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
                    .nickname(member.getNickname())
                    .createdAt(member.getCreatedAt())
                    .msg("로그인 성공")
                    .build()
    );
  }

  public boolean isValidateEmail(MemberRequestDto requestDto) {
    return isPresentEmail(requestDto.getEmail()) == null ? true : false;
  }

  public boolean isValidateNickname(MemberRequestDto requestDto) {
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

  //카카오 로그인
  public TokenDto kakaoLogin(String code) throws JsonProcessingException {
    // 1. "인가 코드"로 "액세스 토큰" 요청
    String accessToken = getAccessToken(code);
    // 2. 토큰으로 카카오 API 호출
    KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

    // DB 에 중복된 Kakao Id 가 있는지 확인
    Long kakaoId = kakaoUserInfo.getId();
    Member kakaoUser = memberRepository.findByKakaoId(kakaoId)
            .orElse(null);
    if (kakaoUser == null) {
      // 회원가입
      // username: kakao nickname
      String nickname = kakaoUserInfo.getNickname();

      // password: random UUID
      String password = UUID.randomUUID().toString();
      String encodedPassword = passwordEncoder.encode(password);

      // email: kakao email
      String email = kakaoUserInfo.getEmail();
      // role: 일반 사용자

      kakaoUser = new Member(nickname, encodedPassword, email, kakaoId);
      memberRepository.save(kakaoUser);
    }
    // 4. 강제 kakao로그인 처리
    UserDetails userDetails = new UserDetailsImpl(kakaoUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Member member = isPresentEmail(kakaoUser.getEmail());
    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    return tokenDto;
  }


//@Value("${myKaKaoApiKey}")
//private String myKakaoApiKey;

  private String getAccessToken(String code) throws JsonProcessingException {

    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP Body 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "74c0de2127c02a656ddaae72170f98d5");
    body.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
    body.add("code", code);

    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<>(body, headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
    );

    // HTTP 응답 (JSON) -> 액세스 토큰 파싱
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    return jsonNode.get("access_token").asText();
  }


  private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String.class
    );

    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    Long id = jsonNode.get("id").asLong();
    String nickname = jsonNode.get("properties")
            .get("nickname").asText();
    String loginId = jsonNode.get("kakao_account")
            .get("email").asText();

    System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + loginId);
    return new KakaoUserInfoDto(id, nickname, loginId);
  }

}
