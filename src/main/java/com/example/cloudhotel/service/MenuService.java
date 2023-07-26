package com.example.cloudhotel.service;

import com.example.cloudhotel.model.Menu;
import com.example.cloudhotel.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * packageName : com.example.cloudhotel.service
 * fileName : MenuService
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
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    //    전체조회함수
    public List<Menu> findAll(){
        List<Menu> list = menuRepository.findAll();
        return list;
    }
}
