package plming.board.boardApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.boardApply.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationCustomRepository {

}