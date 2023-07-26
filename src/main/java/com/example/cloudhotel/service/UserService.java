package com.example.cloudhotel.service;

import com.example.cloudhotel.model.User;
import com.example.cloudhotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : UserService
 * author : 605
 * date : 2023-07-07
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-07-07         605          최초 생성
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    //    전체조회함수
    public Page<User> findAll(Pageable pageable){
        Page<User> page = userRepository.findAll(pageable);
        return page;
    }

    //    email like 검색 : 전체조회페이지
    public Page<User> findAllByEmailContaining(String email, Pageable pageable) {
        Page<User> page
                = userRepository
                .findAllByEmailContaining(email, pageable);

        return page;
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public User save(User user) {
        User user2 = userRepository.save(user);

        return user2;
    }

    //   기본키로 상세 조회(1건조회) 함수 : 리액트 상세 페이지
    public Optional<User> findById(long uno) {
        Optional<User> optionalUser
                = userRepository.findById(uno);

        return optionalUser;
    }

    //   멤버id(uno) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(long uno) {
        if(userRepository.existsById(uno) == true) {
//            삭제 실행
            userRepository.deleteById(uno);
            return true;
        } else {
            return false;
        }
    }

//    회원 정보 수정함수
    public void updateUser(String username,
                          String email,
                          String phone,
                         long uno){
       userRepository.selectUpdateUser(username, email, phone, uno);

    }
}
