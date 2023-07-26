package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.ERole;
import com.example.cloudhotel.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName : com.example.simplelogin.repository
 * fileName : RoleRepository
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
public interface RoleRepository extends JpaRepository<Role, Integer> {
//    권한 이름으로 상세조회 함수
    Optional<Role> findByName(ERole name);
}
