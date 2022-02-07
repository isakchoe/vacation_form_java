package kakaoStyle.vacation.domain.vacation;

import kakaoStyle.vacation.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
public class Vacation {


    @Id
    @GeneratedValue
    @Column(name = "vacation_id")
    private Long id;


//    휴가 신청 일시
    private float dayoff;

//    시작일, 종료일 만들기

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    Vacation( float dayoff, User user){
        this.dayoff = dayoff;
        this.user = user;
    }

}