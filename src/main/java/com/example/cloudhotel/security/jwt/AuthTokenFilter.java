package com.example.cloudhotel.security.jwt;

import com.example.cloudhotel.security.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// JWT : 웹토큰에 유저인증정보와 디지털 서명을 넣어 , 서버에서는 비밀키로 인증된 웹 토큰인지 인증만하고
//       클라이언트는 웹 토큰을 지니고 다니면서 사이트의 다양한 메뉴에 접근할 수 있게하는 인증 방식( sessionless 방식 )
//   vs 쿠키 + 세션 방식에 비해 속도가 빠르고, 간편한 인증을 할 수 있어 현재 많이 사용되는 방식
//    Json Web Token 구조 : 헤더(header).내용(payload).서명(signature)
//    헤더 : 토큰타입, 알고리즘
//    내용 : 데이터(subject(주체(이름))), 토큰발급대상(issuedAt), 만료기간(expiration), 토큰수령자
//    서명 : Jwts.builder().signWith(적용할_암호화알고리즘, 비밀키값) : 암호화알고리즘 과 비밀키값으로 웹토큰 정의
//    생성 : Jwts.builder().compact()                         : 토큰 생성
// JWT 토큰 인증을 위한 필터 클래스 :  , 스프링에 기본적으로 없으므로 만들어 주어야함 ( 기본 : 세션 필터 )
// => WebSecurityConfig 에 웹토큰 필터로써 등록됨

// OncePerRequestFilter : 요청 당 반드시 한번만 인증/인가 로직을 실행하게 보장함(여러번 검증하면 성능 저하되기 때문에)
// 예) 요청에 대해 인증/권한체크 후(서브 요청 1) 특정 url로 리다이렉트할때(서브 요청 2) 보통은 인증/권한체크가 2번 일어남 이때
//     마지막은 불필요하므로 한번만 인증/권한체크가 일어나게 보장하게 만들어주는 인터페이스
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils; // JWT 객체(유용한 속성, 함수가 있음)

  @Autowired
  private UserDetailsServiceImpl userDetailsService; // DB 인증을 위한 함수가 있는 객체

//  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

//  Json Web Token 필터 만들어 SecurityContextHolder 에 새로운 JWT 필터 저장
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
//      웹토큰 받아서 문자열로 변환
//      사용법) parseJwt(객체) : 객체를 문자열로 변환
      String jwt = parseJwt(request);
//      1. 웹토큰 유효성 체크해서
//      2. 유효하면 DB에서 유저 있는 지 조회
//      3. 조회된 유저를 인증된 유저로 인증마크 붙여서 홀더에 넣음

//      jwt 토큰이 있고, 유효하면
//      jwtUtils.validateJwtToken(웹토큰) :
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//        웹토큰에서 유저 id 꺼냄
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

//        유저 id로 db 조회해서 userDetails에 넣음
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//      인증된 객체로 반환 : new UsernamePasswordAuthenticationToken() 매개변수 3개짜리 생성자 효출하면 강제 인증 성공 authenticated = true 로 설정됨
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
//        request 추가 정보를 가지고 인증에 활용하기 위한 객체
//        우리는 기본으로 사용할 것이므로 아래과 같이 request 객체만 사용
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//        인증된 authentication 객체를 홀더에 넣어둠
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

//    필터체인으로 연결하여 줍니다.
//    체인에 필터를 실행하고 체인의 가장 마지막에는 클라이언트가 요청한 최종 자원이 위치
    filterChain.doFilter(request, response);  // 필터 실행
  }

//  네트웍으로 전송된 헤더 데이터에 "Bearer" 있고
//  "Authorization" 다음 문자열이 있으면 7부터 헤더의 길이만큼 잘라서 리턴함
  private String parseJwt(HttpServletRequest request) {
//    Authorization 이 있는 헤더 객체 가져오기
    String headerAuth = request.getHeader("Authorization");

//    "Authorization" 이 헤더에 있고 Bearer 과 있으면
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//      헤더의 7번째 부터 끝까지 문자열을 잘라서 리턴 : 웹토큰
      return headerAuth.substring(7, headerAuth.length());
    }

    return null;
  }
}
