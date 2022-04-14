package plming.comment.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.comment.repository.CommentCustomRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {
}