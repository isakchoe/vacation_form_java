package kakaoStyle.vacation.domain.repository;

import kakaoStyle.vacation.domain.entity.user.User;
import kakaoStyle.vacation.domain.entity.vacation.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {

    List<Vacation> findAllByUser(User user);
}

