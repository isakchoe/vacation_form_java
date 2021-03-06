package kakaoStyle.vacation.domain.entity.vacation;

import kakaoStyle.vacation.domain.entity.user.User;
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


    // 휴가 신청일 수
    private double dayoff;

    @Column(name = "start_day")
    private java.sql.Date startday;

    @Column(name = "end_day")
    private java.sql.Date endday;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    Vacation( double dayoff, User user, java.sql.Date startday, java.sql.Date endday ){
        this.dayoff = dayoff;
        this.user = user;
        this.startday = startday;
        this.endday = endday;
    }

}
