package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Like;
import com.example.cloudhotel.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : LikeService
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
@Service
public class LikeService {

    //    속성 정의 : DB 접속 (CRUD)
    @Autowired
    LikeRepository likeRepository;

    //    id 로 전체조회함수
    public List<Like> findAllLikesById(String id) {
        return likeRepository.findAllById(id);
    }

    //    저장 함수 : 리액트 추가페이지, 상세페이지
    public Like save(Like like) {
        Like like2 = likeRepository.save(like);

        return like2;
    }

    //   관심항목 기본키(lno) 로 삭제하는 함수 : 리액트 상세 페이지
    public boolean removeById(int lno) {
        if(likeRepository.existsById(lno) == true) {
            Like like = likeRepository.CheckByLnoAndId(lno);
//            삭제 실행
            likeRepository.deleteById(lno);
            return true;
        } else {
            return false;
        }
    }
}
