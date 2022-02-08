package kakaoStyle.vacation.domain.web.dto;

import kakaoStyle.vacation.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

@Setter
@Getter
public class VacationDto {
//    현재 로그인 사용자의 정보 빼오기
    private User user;
    private float dayoff;
//    dto --- html form 일치하는지 확인! 특히 대문자.
    private java.sql.Date startday;
    private java.sql.Date endday;

    private


//





}
