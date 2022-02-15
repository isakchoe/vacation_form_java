package kakaoStyle.vacation.domain.web.controller;

import com.fasterxml.jackson.core.JsonToken;
import kakaoStyle.vacation.domain.service.UserService;
import kakaoStyle.vacation.domain.service.VacationService;
import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class VacationController {


    private final VacationService vacationService;
    private final UserService userService;

//    데이터 저장
    @PostMapping("/vacationlist")
    public String createVacation(@AuthenticationPrincipal User user, VacationDto vacationDto){


//        남은휴가 >= 신청휴가일경우만 승인.
        if(user.getLeftVacation() >= vacationDto.getDayoff()){
            vacationDto.setUser(user);
            vacationService.save(vacationDto);

//            고치기,,  blank --> null true 하고. null  아니면 우선하는걸로!!

//            에러 처리(사용자의 잘못된 입력)
            if (dayOff == 0){
                return "redirect:/vacation/form";
            }

//        user 휴가 차감,,,, jpa update!! 참고하기
            userService.minusLeftvacation(user, dayOff);
            return "redirect:/vacationlist";
        }
        else{
//            alret 띄우기 알아보기
            return "redirect:/vacation/form";
        }
    }

    @PostMapping("/delete/{idx}")
    public String deleteVacation(@AuthenticationPrincipal User user, @PathVariable("idx") Long vacationId ){
//        증가하 고 삭제하 기
        userService.plusLeftVacation(user, vacationId);
        vacationService.cancleVacation(vacationId);
        return "redirect:/vacationlist";

    }

    @GetMapping("/vacation/{idx}")
    public String detailVacation(@PathVariable("idx") Long vacationId, Model model){

        Vacation vacation = vacationService.findById(vacationId);
        model.addAttribute("vacation", vacation);

        return "vacationIndex";
    }


    @GetMapping("/vacationlist")
    public String vacationList(@AuthenticationPrincipal User user, Model model){

        model.addAttribute("user", user);
        List<Vacation> vacations = vacationService.findAllVacations(user);
        model.addAttribute("vacations", vacations);
        return "vacationList";
    }



    @GetMapping("/vacation/form")
    public String createVacation() {
        return "vacationForm";
    }


}
