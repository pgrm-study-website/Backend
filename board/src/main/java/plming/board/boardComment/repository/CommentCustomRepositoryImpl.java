package plming.board.boardComment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.board.boardComment.entity.Comment;

import java.util.List;

import static plming.board.boardComment.entity.QComment.comment;

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
                .orderBy(comment.id.desc())
                .fetch();
    }

    @Override
    public List<Comment> findRecommentByCommentId(Long commentId) {

        return jpaQueryFactory.selectFrom(comment)
                .where(comment.parentId.eq(commentId))
                .orderBy(comment.id.desc())
                .fetch();
    }

    @Override
    public List<Long> findCommentBoardByUserId(Long userId) {

        return jpaQueryFactory.select(comment.board.id).from(comment)
                .where(comment.user.id.eq(userId), comment.deleteYn.eq('0'))
                .distinct()
                .orderBy(comment.board.id.desc())
                .fetch();
    }

    @Override
    public Long updateCommentByCommentId(Long commentId, String content) {

        jpaQueryFactory.update(comment)
                .set(comment.content, content)
                .where(comment.id.eq(commentId))
                .execute();

        return jpaQueryFactory.select(comment.id).from(comment)
                .where(comment.id.eq(commentId))
                .fetchOne();
    }

    @Override
    public void deleteCommentByCommentId(Long commentId) {

        jpaQueryFactory.update(comment)
                .set(comment.content, "삭제된 댓글입니다.")
                .set(comment.deleteYn, '1')
                .where(comment.id.eq(commentId))
                .execute();
    }
}