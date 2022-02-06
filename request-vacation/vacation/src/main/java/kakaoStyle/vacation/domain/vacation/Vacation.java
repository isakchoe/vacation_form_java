package kakaoStyle.vacation.domain.vacation;

import kakaoStyle.vacation.domain.user.User;

import javax.persistence.*;

@Entity
public class Vacation {


    @Id
    @GeneratedValue
    @Column(name = "vacation_id")
    private Long id;

    private String applicant;

//    휴가 신청 일시
    private float dayoff;

//    시작일, 종료일 만들기

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
