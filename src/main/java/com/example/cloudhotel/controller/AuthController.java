package com.example.cloudhotel.controller;

import com.example.cloudhotel.dto.request.LoginRequest;
import com.example.cloudhotel.dto.request.SignupRequest;
import com.example.cloudhotel.dto.response.JwtResponse;
import com.example.cloudhotel.dto.response.MessageResponse;
import com.example.cloudhotel.model.ERole;
import com.example.cloudhotel.model.Role;
import com.example.cloudhotel.model.User;
import com.example.cloudhotel.repository.RoleRepository;
import com.example.cloudhotel.repository.UserRepository;
import com.example.cloudhotel.security.jwt.JwtUtils;
import com.example.cloudhotel.security.services.UserDetailsImpl;
import com.example.cloudhotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static org.aspectj.bridge.MessageUtil.debug;


//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  
  // 스프링 시큐리티에서 사용하는 인증/권한체크 처리를 위한 객체  
  @Autowired
  AuthenticationManager authenticationManager; 

  @Autowired
  UserRepository userRepository; // 유저 레포지토리

  @Autowired
  RoleRepository roleRepository; // 롤 레포지토리

  @Autowired
  PasswordEncoder encoder;  // 암호화 객체

  @Autowired
  JwtUtils jwtUtils;  // 웹 토큰 객체

  @Autowired
  UserService userService;

//  로그인 함수 : GET 방식 아닌 POST 방식으로 객체를 body 에 숨겨서 전송됨
//  조회(select) -> Get 방식(@GetMapping) 권고
//  예) http://localhost:8000/api/signin?id="forbob"&password=123456 (주소창에 정보가 보임)
//  보안 (Post 방식) -> http 의 바디에 문서를 넣어서 보내는 방식 (주소창에 정보 안 보임)
//  @Valid 로 서버 유효성 체크
  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {


//   TODO: 1) 인증 시작 : 스프링 시큐리티에서 제공하는 인증 객체
//    스프링 시큐리티가 DB 접근하여(id/pw) 우리 사용자가 맞으면 인증태그를 붙여서 인증객체에 넣어줌(return 해줌)
    Authentication authentication = authenticationManager.authenticate(
            // 아이디와 패스워드로, Security 가 알아 볼 수 있는 token 객체로 생성해서 인증처리
//            UsernamePasswordAuthenticationToken 의 매개변수로 유저명/패스워드 전달
        new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword()));

//  TODO: 2) 인증된 객체(authentication)를 홀더에 저장
//    홀더(필통) : 인증된 객체들의 모임
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
//    TODO :  3) 웹토큰 발행
//    JWT 토큰(웹토큰) 발행 : ( 나중에 리액트로 전달함)
//    generateJwtToken(인증된 객체) : 웹토큰 생성하는 함수 호출
//    return 값 : 웹토큰(jwt) 문자열 리턴
    String jwt = jwtUtils.generateJwtToken(authentication);

// TODO : 5)   authentication.getPrincipal() : 인증된 유저 정보 가져오기
//    인증된 유저 정보를 userDetails 에 저장
//     유저 정보 == userDetails == authentication.getPrincipal()
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//  TODO : 5-2)  권한이 배열로 관리됨 : USER_ROLE, ADMIN_ROLE
//    권한 가져와서 roles 에 저장
//    roles : 권한 배열
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

//    클라이언트에 인증된 사용자 정보 전송(토큰 + 사용자 정보(권한 포함) :
//         JwtResponse DTO 객체에 정보를 담아 전송
    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getuno(),
                         userDetails.getUsername(), 
                         userDetails.getId(),
                         userDetails.getEmail(),
                         userDetails.getPhone(),
                         roles));
  }

//  새 사용자 등록 함수 : insert
//  @PostMapping 사용 : 회원가입
//  SignupRequest : 회원가입용 DTO ( 유저명, 이메일 , 패스워드 )
  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

    //  TODO :  1) id가 DB 에 있는 지 확인
    if (userRepository.existsById(signUpRequest.getId())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Id is already in use!"));
    }

//  TODO : 2)  이메일이 DB 에 있는 지 확인
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // TODO : 3) 새로운 유저 생성 : 패스워드 암호화
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
//       encoder.encode("pw") : pw 를 암호화
            encoder.encode(signUpRequest.getPassword()),

            signUpRequest.getId(),
            signUpRequest.getPhone()
               );

//    TODO : 4) 새로운 권한 만들기
//    혹시 signUpRequest(리액트 정보) 에 권한이 있으면 가져오기
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>(); // 권한 배열(집합)

//    리액트에서 보내준 권한(strRoles)이 없으면
    if (strRoles == null) {
//      권한이 없으면(새로운 일반유저)로 만듦
//      새로운 유저권한을 가져와서 roles 자료구조(집합)에 넣기
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
//    리액트에서 권한을 보내줬으면 : 지정된 권한을 부여해서 유저 생성
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
// TODO : 6) 최종 생성된 권한을 유저에 setter 이용해서 추가
    user.setRoles(roles);
//    DB에 새로운 유저 저장
    userRepository.save(user);
//  TODO: 7) 리액트로 성공메세지 전송
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }



//  //////////////////////////////////////////////////////////////////////////////

  //    1) 전체조회 + like 검색 함수 : 전체조회페이지
//     1)전체 조회 함수 + 2) email like 검색 함수(페이징처리)
  @GetMapping("/user")
  public ResponseEntity<Object> getUserAll(
          @RequestParam(required = false, defaultValue = "") String email,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    try {
      Pageable pageable = PageRequest.of(page, size);

      Page<User> userPage
              = userService.findAllByEmailContaining(email, pageable);

      Map<String, Object> response = new HashMap<>();
      response.put("user", userPage.getContent()); // user객체배열
      response.put("currentPage", userPage.getNumber()); // 현재페이지번호
      response.put("totalItems", userPage.getTotalElements()); // 총개수(건수)
      response.put("totalPages", userPage.getTotalPages()); // 총페이지수

      if (userPage.isEmpty() == false) {
        // 성공
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        // 데이터 없음
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

    } catch (Exception e) {

      debug(e.getMessage()); // 에러 출력
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //   회원 상세조회 함수
  @GetMapping("/user/{uno}")
  public ResponseEntity<Object> getUserId(@PathVariable long uno) {
    try {
      // 저장 서비스 함수 호출(수정)
      Optional<User> optionalUser
              = userService.findById(uno);

      if(optionalUser.isPresent() == true) {
//                성공( optionalUser.get() : 객체 꺼내기 함수 )
        return new ResponseEntity<>(optionalUser.get(), HttpStatus.OK);
      } else {
//                데이터 없음(NO_CONTENT:204)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      debug(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  //   회원 삭제 함수
  @DeleteMapping("/user/deletion/{uno}")
  public ResponseEntity<Object> deleteUser(
          @PathVariable long uno
  ) {
    try {
      // 삭제 서비스 함수 호출
      boolean bSuccess = userService.removeById(uno);

      if(bSuccess == true) {
        // 삭제 성공
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        // 삭제할 데이터 없음
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      debug(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

//  회원 정보 수정 함수
  @PutMapping("/user/update")
  public ResponseEntity<Object> updateUser(@RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("phone") String phone,
                                           @RequestParam("uno") long uno){
    try {

      userService.updateUser(username, email, phone, uno);
      return new ResponseEntity<>(HttpStatus.OK);
    }catch (Exception e){
      debug(e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
