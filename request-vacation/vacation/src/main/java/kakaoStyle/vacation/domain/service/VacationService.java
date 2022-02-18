package kakaoStyle.vacation.domain.service;


import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

//transactional 고려하기
@RequiredArgsConstructor
@Service
public class VacationService {

    private final VacationRepository vacationRepository;


    // 휴가저장
    @Transactional(rollbackFor = Exception.class)
    public void save(VacationDto vacationDto){
         vacationRepository.save(Vacation.builder()
                .user(vacationDto.getUser())
                .dayoff(vacationDto.getDayoff())
                .startday(vacationDto.getStartday())
                .endday(vacationDto.getEndday()).build());
    }


    //   휴가취소
    @Transactional(rollbackFor = Exception.class)
    public void cancleVacation(Long vacationId){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
    //        휴가 엔티티 delete
        vacationRepository.delete(vacation);
    }

    // 휴가 취소 가능한가 확인
    public boolean isPossibleDelete(Long vacationId){
        Vacation vacation = findById(vacationId);

        //  포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        //   휴가시작일
        java.sql.Date startday = vacation.getStartday();
        String fmtStartDay = fmt.format(startday);

        //  현재 시각
        LocalDate now = LocalDate.now();
        String formatNow = now.format(formatter);

        //   비교
        int nowInt = Integer.parseInt(formatNow);
        int startDayInt = Integer.parseInt(fmtStartDay);

        if(startDayInt > nowInt){
            return true;
        }
        return false;
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
