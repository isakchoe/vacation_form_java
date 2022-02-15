package kakaoStyle.vacation.domain.service;


import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//transactional 고려하기
@RequiredArgsConstructor
@Service
public class VacationService {

    private final VacationRepository vacationRepository;


//    저장
    public Long save(VacationDto vacationDto){


        float result = vacationDto.calculateHoliday(vacationDto.getStartday(), vacationDto.getEndday());

//        반차, 반반차일 경우
        if(vacationDto.getEndday() == vacationDto.getStartday()){
            result = vacationDto.getDayoff();
        }


        return vacationRepository.save(Vacation.builder()
                .user(vacationDto.getUser())
                .dayoff(result)
                .startday(vacationDto.getStartday())
                .endday(vacationDto.getEndday()).build()).getId();
    }

//    휴가취소
    @Transactional
    public void cancleVacation(Long vacationId){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
//        휴가 엔티티 delete
        vacationRepository.delete(vacation);
    }


//   유저가 신청한 모든 휴가
   public List<Vacation> findAllVacations(User user){
        List<Vacation> vacations = vacationRepository.findAllByUser(user);
        return vacations;
    }

    public Vacation findById(Long vacationId){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
        return vacation;
    }
}
