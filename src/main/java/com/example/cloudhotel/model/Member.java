package com.example.cloudhotel.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.cloudhotel.model
 * fileName : Member
 * author : 605
 * date : 2023-06-21
 * description : 회원
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-06-21         605          최초 생성
 */
@Entity
@Table(name = "TB_MEMBER")
@SequenceGenerator(
        name = "SQ_MEMBER_GENERATOR"
        , sequenceName = "SQ_MEMBER"
        , initialValue = 1
        , allocationSize = 1
)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "DELETE_YN = 'N'")
@SQLDelete(sql = "UPDATE TB_MEMBER SET DELETE_YN = 'Y', DELETE_TIME=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE MNO = ?")


public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_MEMBER_GENERATOR"
    )
    @Column
    private Integer mno;    // 회원번호
    @Column
    private String id;      // 아이디

    private String password;      // 비밀번호

    private String username;      // 이름
    @Column
    private String email;      // 이메일
    @Column
    private String phone;      // 전화번호

}
