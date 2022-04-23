package plming.board.boardApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.boardApply.entity.Application;
import plming.user.entity.User;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationCustomRepository {
    void deleteAllByUser(User user);
}