package kakaoStyle.vacation.domain.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
// like-- modelserializer 데이터를 받는 상자
public class UserDto {

    private String email;
    private String password;
    private String auth;
    private float leftVaction;



}
