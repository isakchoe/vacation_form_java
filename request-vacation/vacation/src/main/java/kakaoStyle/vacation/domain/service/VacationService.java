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
                .dayoff(vacationDto.getDayoff())
                .startday(vacationDto.getStartday())
                .endday(vacationDto.getEndday()).build()).getId();

    }
}
