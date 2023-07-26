package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Faq;
import com.example.cloudhotel.repository.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : FaqService
 * author : 605
 * date : 2023-06-15
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-15         605          최초 생성
 */
@Service
public class FaqService {
    @Autowired
    FaqRepository faqRepository;

    //    전체조회함수
    public List<Faq> findAll(){
        List<Faq> list = faqRepository.findAll();
        return list;
    }
}
