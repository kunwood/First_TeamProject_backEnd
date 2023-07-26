package com.example.cloudhotel.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * packageName : com.example.simplelogin.model
 * fileName : Role
 * author : 605
 * date : 2023-06-01
 * description : 권한 모델
 * 요약 :
 *      자바 상수 : enum 자료형 ( 상수 )
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-01         605          최초 생성
 */
@Entity
@SequenceGenerator(
        name = "SQ_ROLE_GENERATOR"
        , sequenceName = "SQ_ROLE"
        , initialValue = 1
        , allocationSize = 1
)
@Table(name = "TB_ROLE")
@Getter
@Setter
@NoArgsConstructor
public class Role {
//    속성 정의
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator ="SQ_ROLE_GENERATOR" )
    @Column
    private Integer uno; //권한 id(기본키,시퀀스)

//    @Enumerated(EnumType.STRING) : enum(열거형) 상수 사용하겠다는 어노테이션
//    EnumType.STRING : ( Ordinal(순서) , name(이름) 2종류 있음) 이름을 사용하겠다는 의미
    @Enumerated(EnumType.STRING)
    @Column
    private ERole name; // 권한명 (ROLE_USER, ROLE_ADMIN)

//    생성자 : ID 제외

    public Role(ERole name) {
        this.name = name;
    }
}
