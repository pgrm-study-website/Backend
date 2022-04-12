package plming.comment.repository;

import org.springframework.transaction.annotation.Transactional;
import plming.comment.entity.Comment;

import java.util.List;

public interface CommentCustomRepository {

    /**
     * 게시글 id에 해당하는 댓글 불러오기 - 대댓글 포함 x
     */
    List<Comment> findCommentByBoardId(final Long boardId);

    /**
     * 댓글의 대댓글 불러오기
     */
    List<Comment> findRecommentByCommentId(final Long commentId);

    /**
     * 사용자 id 기준 댓글 불러오기
     */
    List<Long> findCommentBoardByUserId(final Long userId);

    /**
     * 댓글 수정하기
     */
    @Transactional
    Long updateCommentByCommentId(final Long commentId, final String content);

    /**
     * 댓글 Id로 댓글 삭제하기
     */
    @Transactional
    Long deleteCommentByCommentId(final Long commentId);
}
