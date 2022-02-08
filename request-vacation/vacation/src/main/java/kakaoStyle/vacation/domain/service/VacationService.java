package kakaoStyle.vacation.domain.service;


import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.user.User;
import kakaoStyle.vacation.domain.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VacationService {

    private final VacationRepository vacationRepository;

    public Long save(VacationDto vacationDto){


        return vacationRepository.save(Vacation.builder()
                .user(vacationDto.getUser())
                .dayoff(vacationDto.getDayoff()).build()).getId();

//                .email(userDto.getEmail())
//                .auth(userDto.getAuth())
//                .password(userDto.getPassword()).build()).getCode();

    }
}
