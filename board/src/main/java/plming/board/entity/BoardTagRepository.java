package plming.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.repository.BoardCustomRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>, BoardCustomRepository {

}