package plming.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.repository.BoardCustomRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

}