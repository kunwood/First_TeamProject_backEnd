package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.cloudhotel.repository
 * fileName : MenuRepository
 * author : 605
 * date : 2023-06-21
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
}
