package com.example.cloudhotel.repository;

import com.example.cloudhotel.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * packageName : com.example.cloudhotel.repository
 * fileName : LikeRepository
 * author : 605
 * date : 2023-06-27
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-27         605          최초 생성
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {

    List<Like> findAllById(String id);


    //  관심항목에 있는 아이디와 멤버에 있는 아이디 같은지 조회하는 함수
    @Query(value = "SELECT *FROM TB_LIKE l " +
            "INNER JOIN TB_USER u ON l.id = u.id " +
            "WHERE l.LNO = :lno AND l.id = u.id ",nativeQuery = true)
    Like CheckByLnoAndId(@Param("lno") Integer lno);

}
