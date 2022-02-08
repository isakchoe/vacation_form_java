package kakaoStyle.vacation.domain.web.controller;

import kakaoStyle.vacation.domain.service.UserService;
import kakaoStyle.vacation.domain.service.VacationService;
import kakaoStyle.vacation.domain.user.User;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class VacationController {


    private final VacationService vacationService;

//    데이터 저장
    @PostMapping("/vacationlist")
    public String createVacation(@AuthenticationPrincipal User user, VacationDto vacationDto){
        vacationDto.setUser(user);
        vacationService.save(vacationDto);
        return "redirect:/vacationlist";
    }


    @GetMapping("/vacationlist")
    public String vacationList(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);
//        System.out.println(user);
        return "vacationList";
    }



    @GetMapping("/vacation/form")
    public String createVacation(@AuthenticationPrincipal User user, Model model){
        return "vacationForm";
    }


}
