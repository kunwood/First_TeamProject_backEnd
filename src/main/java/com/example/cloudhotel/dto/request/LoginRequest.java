package com.example.cloudhotel.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

// 리액트에 요청하는 정보
@Getter
@Setter
@ToString
public class LoginRequest {

    private String id; // id

    private String password; // 암호


}








