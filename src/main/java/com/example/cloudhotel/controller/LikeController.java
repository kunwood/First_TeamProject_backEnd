package com.example.cloudhotel.controller;

import com.example.cloudhotel.model.Like;
import com.example.cloudhotel.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * packageName : com.example.cloudhotel.controller
 * fileName : LikeController
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
@Slf4j
@RestController
@RequestMapping("/api")
public class LikeController {
    @Autowired
    LikeService likeService;

    @GetMapping("/like")
    public ResponseEntity<Object> getLikeAllById(@RequestParam("id") String id) {
//        에러 처리 : try ~ catch
        try {
//            1) id로 전체 조회 서비스 함수
            List<Like> list
                    = likeService.findAllLikesById(id);

//          전체 조회 결과가 있으면 데이터 + 성공메세지 전송(출력)
            if (list.isEmpty() == false) {
//                데이터 + 성공메세지 출력(전송)
                return new ResponseEntity<>(list, HttpStatus.OK);
            } else {
//                데이터 없음 , 데이터없음 메세지 출력(전송)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.debug(e.getMessage()); // 에러발생시 로그 출력
//          서버 에러메세지 전송 (콘솔 출력)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    새로운 관심목록 추가(저장) 함수 : insert -> @PostMapping 리액트의 추가페이지
    @PostMapping("/like")
    public ResponseEntity<Object> createLike(@RequestBody Like like) {
//        에러처리(예외처리) : try ~ catch
        try {
            Like like2 = likeService.save(like); // 저장 함수 호출
//           CREATED : 201 번 코드
            return new ResponseEntity<>(like2, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage()); // 에러발생시 로그 콘솔 출력
//          상태 메세지 전송(서버에러) : INTERNAL_SERVER_ERROR(500)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   관심목록 삭제 함수 : 리액트의 상세페이지(lno)
    @DeleteMapping("/like/deletion/{lno}")
    public ResponseEntity<Object> deleteLike(@PathVariable int lno){
//        에러처리(예외처리) : try ~ catch
        try {
            boolean bSuccess = likeService.removeById(lno);

            // 삭제가 1건 이상 되면 : true
            // delete 문이 성공했을 경우
            if(bSuccess == true) {
                // HttpStatus.OK : 200
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // delete 문이 실패했을 경우 : 삭제할 데이터가 없음
                // HttpStatus.NO_CONTENT : 203
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.info(e.getMessage()); // 에러발생시 콘솔에 출력
            // 프론트로 상태 메세지 전달(서버에러) : INTERNAL_SERVER_ERROR(500)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
