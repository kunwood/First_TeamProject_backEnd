package com.example.cloudhotel.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
// 스프링부트 -> 리액트 결과전송하는 클래스(객체)
public class JwtResponse {
  private String token;   // 웹토큰(인증정보)
  private String type = "Bearer";  // 고정(웹토큰 관련 속성)
  private Long uno; // 유저 uno(시퀀스)
  private String username;  // 유저명
  private String id;  // 유저명
  private String email;  // 이메일
  private String phone;  // phone
  private List<String> roles;  // 권한 배열(새로운 권한 : ROLE_USER)

//  생성자
  public JwtResponse(String accessToken, Long uno, String username,String id, String email,String phone, List<String> roles) {
    this.token = accessToken;
    this.uno = uno;
    this.username = username;
    this.id = id;
    this.email = email;
    this.phone = phone;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getUno() {
    return uno;
  }

  public void setUno(Long uno) {
    this.uno = uno;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<String> getRoles() {
    return roles;
  }
}
