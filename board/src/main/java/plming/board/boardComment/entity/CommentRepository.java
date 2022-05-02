package plming.board.boardComment.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.board.boardComment.repository.CommentCustomRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}