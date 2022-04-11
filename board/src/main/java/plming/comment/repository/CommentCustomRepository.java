package plming.comment.repository;

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
}
