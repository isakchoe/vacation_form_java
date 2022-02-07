package kakaoStyle.vacation.domain.web.controller;


import kakaoStyle.vacation.domain.service.UserService;
import kakaoStyle.vacation.domain.user.User;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserDto userDto){
        userService.save(userDto);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
//
//    @GetMapping("/vacationlist")
//    public String vacationList(@AuthenticationPrincipal User user, Model model){
//        model.addAttribute("user", user);
////        System.out.println(user);
//        return "vacationList";
//    }
//
}
