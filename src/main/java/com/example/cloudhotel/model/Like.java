package com.example.cloudhotel.model;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * packageName : com.example.cloudhotel.model
 * fileName : Like
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
@Entity
@Table(name = "TB_LIKE")
@SequenceGenerator(
        name = "SQ_LIKE_GENERATOR"
        , sequenceName = "SQ_LIKE"
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
@SQLDelete(sql = "UPDATE TB_LIKE SET DELETE_YN = 'Y', DELETE_TIME=TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE LNO = ?")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE
            , generator = "SQ_LIKE_GENERATOR"
    )

    private Integer lno;    // 관심번호
    @Column
    private String id;      // 아이디

    private String rtype;      // 룸타입

    private String pname;      // 패키지이름

    private String price;      // 가격
}
