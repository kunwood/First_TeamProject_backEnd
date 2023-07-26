package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.User;
import com.example.cloudhotel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * packageName : com.example.simplelogin.repository
 * fileName : UserRepository
 * author : 605
 * date : 2023-06-01
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-01         605          최초 생성
 */
public interface UserRepository extends JpaRepository<User,Long> {
//    유저명으로 조회하는 함수
    Optional<User> findByUsername(String username);

//    id 로 조회하는 함수
Optional<User> findById(String id);

//     유저가 있는지 확인하는 함수 : true/false
    Boolean existsById(String id);

//    이메일이 있는지 확인하는 함수

    Boolean existsByEmail(String email);

    //    email like 검색
    Page<User> findAllByEmailContaining(String email, Pageable pageable);

    //    user에 phone 번호 존재확인
    boolean existsByPhone(String phone);

//    회원 정보 수정 (이름,이메일,폰번호)
    @Transactional
    @Modifying
    @Query(value = "UPDATE TB_USER " +
            "set USERNAME = :username, " +
            "EMAIL = :email, " +
            "PHONE = :phone " +
            "WHERE UNO = :uno ",nativeQuery = true)
    void selectUpdateUser(@Param("username") String username,
                   @Param("email") String email,
                   @Param("phone") String phone,
                   @Param("uno") long uno);

}
