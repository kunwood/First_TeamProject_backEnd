package com.example.cloudhotel.security.services;

import com.example.cloudhotel.model.User;
import com.example.cloudhotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// DB 의 정보와 리액트에서 전달받은 유저를 비교해서 인증을 하는 클래스
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

//  유저 인증을 위한 함수
//  DB 의 정보와 리액트에서 전달받은 유저를 비교해서 인증을 하는 함수
//  비교해서 있으면 UserDetailsImpl 의 build(생성자 대용) 함수로 User 객체 생성
  @Override
  @Transactional
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    //  유저 인증을 위한 함수 ( DB 확인 )
    User user = userRepository.findById(id)
//            Optional 객체의 함수 : .orElseThrow(() -> new UsernameNotFoundException("에러발생_문자열"))
//            orElseThrow(화살표함수(람다식)) : 함수의 결과가 에러라면 화살표함수가 실행됨
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));

    //  DB에 있는 지 확인해서 있으면 UserDetailsImpl 로 User 객체 생성
    return UserDetailsImpl.build(user);
  }

}
