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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@RequiredArgsConstructor
@Controller
public class VacationController {


    private final VacationService vacationService;
    private final UserService userService;

    // 휴가 데이터 저장
    @PostMapping("/vacationlist")
    public String createVacation(@AuthenticationPrincipal User user, VacationDto vacationDto, HttpServletResponse response) throws IOException {

        vacationDto.setDayoff();
        //남은휴가 >= 신청휴가일경우만 승인.
        if(user.getLeftVacation() >= vacationDto.getDayoff()){
            vacationDto.setUser(user);
            vacationService.save(vacationDto);

            // user 휴가 차감,,,,
            userService.minusLeftvacation(user, vacationDto.getDayoff());
            return "redirect:/vacationlist";
        }
        else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('남은휴가보다 길게 신청할 수 없습니다!'); history.go(-1);</script>");
            out.flush();
            return "redirect:/vacation/form";
        }
    }

    @PostMapping("/delete/{idx}")
    public String deleteVacation(@AuthenticationPrincipal User user, @PathVariable("idx") Long vacationId, HttpServletResponse response ) throws IOException {

        boolean isPossibleDelete = vacationService.isPossibleDelete(vacationId);

        //  휴가 취소 가능한 경우
        if(isPossibleDelete) {
            //  남은 휴가 증가하고    신청한 휴가 삭제하기
            userService.plusLeftVacation(user, vacationId);
            vacationService.cancleVacation(vacationId);
        }
        else{
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('시작한 휴가는 취소할 수 없습니다!'); history.go(-1);</script>");
            out.flush();
        }
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
