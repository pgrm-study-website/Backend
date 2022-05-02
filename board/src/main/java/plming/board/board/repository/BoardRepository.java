package plming.board.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

}