package kakaoStyle.vacation.domain.service;

import kakaoStyle.vacation.domain.repository.UserRepository;
import kakaoStyle.vacation.domain.repository.VacationRepository;
import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import kakaoStyle.vacation.domain.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    //  유저 저장
    @Transactional(rollbackFor = Exception.class)
    public Long save(UserDto userDto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        return userRepository.save(User.builder()
                .email(userDto.getEmail())
                .auth(userDto.getAuth())
                .password(userDto.getPassword()).build()).getCode();
    }

    //  유저 남은 휴가 차감
    @Transactional(rollbackFor = Exception.class)
    public void minusLeftvacation(User user, double dayOff){
        user.minusLeftVacation(dayOff);
//        저장해야 update 가능
        userRepository.save(user);
    }

    //  유저 남은 휴가 증가
    @Transactional(rollbackFor = Exception.class)
    public void plusLeftVacation(User user, Long vacationId){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow();
        user.plusLeftVacation(vacation.getDayoff());
        userRepository.save(user);
    }

}
