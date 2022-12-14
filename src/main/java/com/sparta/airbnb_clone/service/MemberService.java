package com.sparta.airbnb_clone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.airbnb_clone.domain.*;
import com.sparta.airbnb_clone.dto.request.KakaoUserInfoDto;
import com.sparta.airbnb_clone.dto.request.LoginRequestDto;
import com.sparta.airbnb_clone.dto.request.MemberRequestDto;
import com.sparta.airbnb_clone.dto.request.TokenDto;
import com.sparta.airbnb_clone.dto.response.MemberResponseDto;
import com.sparta.airbnb_clone.dto.response.ResponseDto;
import com.sparta.airbnb_clone.dto.response.WishResponseDto;
import com.sparta.airbnb_clone.jwt.TokenProvider;
import com.sparta.airbnb_clone.repository.HouseRepository;
import com.sparta.airbnb_clone.repository.MemberRepository;
import com.sparta.airbnb_clone.repository.WishRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final HouseRepository houseRepository;
  private final WishRepository wishRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenProvider tokenProvider;

  @Transactional
  public ResponseDto<?> createMember(MemberRequestDto requestDto) {
    if (!isValidateEmail(requestDto)) {
        return ResponseDto.fail("Email_DUPLICATED", "????????? ????????? ?????????.");
    }

    if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
      return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
          "??????????????? ???????????? ????????? ???????????? ????????????.");
    }

    Member member = Member.builder()
            .email(requestDto.getEmail())
            .nickname(requestDto.getNickname())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .isSuperHost(false)
            .build();
    memberRepository.save(member);

    return ResponseDto.success(
            MemberResponseDto.builder()
                    .nickname(member.getNickname())
                    .msg("???????????? ??????")
                    .build()
    );
  }

  @Transactional
  public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
    Member member = isPresentEmail(requestDto.getEmail());
    if (null == member) {
      return ResponseDto.fail("EMAIL_NOT_FOUND",
          "???????????? ?????? ??? ????????????.");
    }

    if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
      return ResponseDto.fail("INVALID_PASSWORD", "??????????????? ???????????? ????????????.");
    }

    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    tokenToHeaders(tokenDto, response);

    List<Wish> wishes = wishRepository.findAllByMember(member.getMemberId());
    List<WishResponseDto> wishResponseDtoList = new ArrayList<>();

    for (Wish wish : wishes) {
      wishResponseDtoList.add(
              WishResponseDto.builder()
                      .houseId(wish.getHouse().getHouseId())
                      .build()
      );
    }

    return ResponseDto.success(
            MemberResponseDto.builder()
                    .nickname(member.getNickname())
                    .createdAt(member.getCreatedAt())
                    .msg("????????? ??????")
                    .wishes(wishResponseDtoList)
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

  //????????? ?????????
  public TokenDto kakaoLogin(String code) throws JsonProcessingException {
    // 1. "?????? ??????"??? "????????? ??????" ??????
    String accessToken = getAccessToken(code);
    // 2. ???????????? ????????? API ??????
    KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

    // DB ??? ????????? Kakao Id ??? ????????? ??????
    Long kakaoId = kakaoUserInfo.getId();
    Member kakaoUser = memberRepository.findByKakaoId(kakaoId)
            .orElse(null);
    if (kakaoUser == null) {
      // ????????????
      // username: kakao nickname
      String nickname = kakaoUserInfo.getNickname();

      // password: random UUID
      String password = UUID.randomUUID().toString();
      String encodedPassword = passwordEncoder.encode(password);

      // email: kakao email
      String email = kakaoUserInfo.getEmail();
      // role: ?????? ?????????

      kakaoUser = new Member(nickname, encodedPassword, email, kakaoId);
      memberRepository.save(kakaoUser);
    }
    // 4. ?????? kakao????????? ??????
    UserDetails userDetails = new UserDetailsImpl(kakaoUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    Member member = isPresentEmail(kakaoUser.getEmail());
    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
    return tokenDto;
  }


//@Value("${kakaoapi}")
//private String myKakaoApiKey;

  private String getAccessToken(String code) throws JsonProcessingException {

    // HTTP Header ??????
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP Body ??????
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "74c0de2127c02a656ddaae72170f98d5");
    body.add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao");
    body.add("code", code);

    // HTTP ?????? ?????????
    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
            new HttpEntity<>(body, headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
    );

    // HTTP ?????? (JSON) -> ????????? ?????? ??????
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    return jsonNode.get("access_token").asText();
  }


  private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

    // HTTP Header ??????
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // HTTP ?????? ?????????
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

    System.out.println("????????? ????????? ??????: " + id + ", " + nickname + ", " + loginId);
    return new KakaoUserInfoDto(id, nickname, loginId);
  }

  @Transactional
  public Double addPoint(Member host) {
    Double point = 0.0;
    List<House> houses = houseRepository.findAllByHost(host);
    for (House house : houses) {
      point += house.getStarAvg();
    }
    point /= houses.size();
    return point;
  }

  @Transactional
  public Member validateMember() {
    return tokenProvider.getMemberFromAuthentication();
  }
}
