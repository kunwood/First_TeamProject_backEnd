package com.example.cloudhotel.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName : com.example.dongsungsi.controller
 * fileName : WebConfig
 * author : kangtaegyung
 * date : 2022/06/14
 * description : CORS(웹 보안)을 허용하는 설정 파일(자바)
 *      CORS : 기본 웹브라우저에 내장된 기능(보안)
 *      목적 : 해킹방지, 피싱방지
 *      내용 : ip주소(컴퓨터 아는 주소), dns(사람이 아는 주소), port
 *      기본적으로 한 사이트(네이버,다음 등)에서는 기본주소가 변경되면 안 됨
 *      react(localhost:3000) <---> springboot(localhost:8000)
 *      ex) http://localhost:3000 -> http://localhost:8000 (포트 바뀜) => CORS 블럭킹
 *                                -> http://127.0.0.3:3000 (ip 바뀜) CORS 블럭킹
 *     피싱사례) 게시판에 글올릴때 http://137.0.0.3 (페이지 전환)
 *              피싱사이트 : 계좌번호,비밀번호 탈취
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/06/14         kangtaegyung          최초 생성
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    아래 * : 모든 url에 대해서 (접근했을때, 주소창에 주소를 넣었을때)

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                아래 url 허용 (리액트 서버의 주소)
                .allowedOrigins("http://localhost:3000")
////                TODO: AWS 배포주소
//                .allowedOrigins("http://35.174.200.96:3000/")
//                TODO: OCI 배포주소
//                .allowedOrigins("http://146.56.111.26:3000/")
//                Todo: 아래 추가해야 update, delete, insert, select 가 cors 문제가 안생김
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.PATCH.name()
                );
    }
}
