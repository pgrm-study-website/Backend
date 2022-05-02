package plming.board.boardTag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.boardTag.entity.BoardTag;
import plming.board.board.repository.BoardCustomRepository;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long>, BoardCustomRepository {

}