package plming.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.comment.entity.Comment;

import java.util.List;

import static plming.comment.entity.QComment.comment;

@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {

        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Comment> findCommentByBoardId(Long boardId) {

        return jpaQueryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardId), comment.parentId.isNull())
                .fetch();
    }

    @Override
    public List<Comment> findRecommentByCommentId(Long commentId) {

        return jpaQueryFactory.selectFrom(comment)
                .where(comment.parentId.eq(commentId))
                .fetch();
    }
}
