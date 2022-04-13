package plming.comment.service;

import plming.comment.dto.CommentRequestDto;
import plming.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    public Long registerComment(CommentRequestDto params);
    public boolean deleteComment(Long id);
    public List<Comment> getCommentList(Comment params);
}
