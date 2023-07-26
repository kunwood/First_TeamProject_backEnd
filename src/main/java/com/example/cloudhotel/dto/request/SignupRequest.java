package com.example.cloudhotel.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

// 리액트의 회원가입에 대한 정보 클래스(객체)
@Getter
@Setter
@ToString
public class SignupRequest {

  private String username; // 유저명

  private String email; // 이메일

  private Set<String> role; //권한 (null: 회원가입시 권한 입력란이 없었음) , 집합 자료구조(일종의 배열)

  private String password; //암호
  private String id; // id
  private String phone; // phone
}
