package plming.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.repository.BoardCustomRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long>, BoardCustomRepository {

}