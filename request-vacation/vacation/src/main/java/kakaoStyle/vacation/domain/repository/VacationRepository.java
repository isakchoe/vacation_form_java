package kakaoStyle.vacation.domain.repository;

import kakaoStyle.vacation.domain.user.User;
import kakaoStyle.vacation.domain.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
//    Optional<Vacation> find(String email);
}

