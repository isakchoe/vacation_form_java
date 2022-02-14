package kakaoStyle.vacation.domain.service;


import kakaoStyle.vacation.domain.repository.UserRepository;
import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.user.User;
import kakaoStyle.vacation.domain.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//transactional 고려하기
@RequiredArgsConstructor
@Service
public class VacationService {
    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;

    public Long save(VacationDto vacationDto){

       User user = vacationDto.getUser();

        return vacationRepository.save(Vacation.builder()
                .user(vacationDto.getUser())
                .dayoff(vacationDto.getDayoff())
                .startday(vacationDto.getStartday())
                .endday(vacationDto.getEndday()).build()).getId();

    }

    @Transactional
    public void cancleVacation(Long vacationId){

        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
//        user 엔티티 update,,, 휴가 취소--> 사용자 휴가 증가 로직
        float wishDayoff = vacation.getDayoff();
        vacation.cancle(wishDayoff);



//        휴가 엔티티 delete
        vacationRepository.delete(vacation);
    }
}
