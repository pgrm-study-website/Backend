package plming.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.repository.ApplicationCustomRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>, ApplicationCustomRepository {

}