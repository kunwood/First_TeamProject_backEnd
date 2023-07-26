package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Combo;
import com.example.cloudhotel.repository.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * comboName : com.example.cloudhotel.service
 * fileName : ComboService
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
@Service
public class ComboService {
    @Autowired
    ComboRepository comboRepository;

    //    전체조회함수
    public List<Combo> findAll(){
        List<Combo> list = comboRepository.findAll();
        return list;
    }

    //   기간조회
    public Page<Combo> selectByDate(String checkIn, String checkOut, Pageable pageable ){
        Page<Combo> page = comboRepository.selectByDate(checkIn, checkOut, pageable);
        return page;
    }

    //   이름/키워드로 like 검색
    public Page<Combo> selectBypname(String pname,String theme, Pageable pageable ){
        Page<Combo> page = comboRepository.selectBypname(pname,theme, pageable);
        return page;
    }

//    패키지 번호로 삭제하는 함수
public boolean removeById(int pno){
    if (comboRepository.existsById(pno) == true){
//            삭제 실행
        comboRepository.deleteById(pno);
        return true;
    }else {
        return false;
    }
}

    //    저장 함수 : 수정
  public Combo save(Combo combo){
      Combo combo2 = comboRepository.save(combo);
      return combo2;
  }

//  패키지 + 추가사항 가격 합계 함수
    public int selectSumCombo(String pname){
        int sum = comboRepository.selectSumCombo(pname);
        return sum;
    }


}
