package kakaoStyle.vacation.domain.service;

import kakaoStyle.vacation.domain.repository.UserRepository;
import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import kakaoStyle.vacation.domain.web.dto.VacationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final VacationRepository vacationRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public Long save(UserDto userDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        return userRepository.save(User.builder()
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword()).build()).getCode();
    }

    public void minusLeftvacation(User user, double dayOff){
        user.minusLeftVacation(dayOff);
//        저장해야 update 가능
        userRepository.save(user);
    }

    public void plusLeftVacation(User user, Long vacationId){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
        user.plusLeftVacation(vacation.getDayoff());
        userRepository.save(user);
    }

}
