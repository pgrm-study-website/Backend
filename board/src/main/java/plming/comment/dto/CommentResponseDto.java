package plming.comment.dto;

import lombok.Getter;
import plming.board.entity.Board;
import plming.comment.entity.Comment;
import plming.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private Long postId;
    private Long userId;
    private Long parentId;
    private String content;
    private char deleteYn;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment entity, Board board, User user) {
        this.id = entity.getId();
        this.postId = board.getId();
        this.userId = user.getId();
        this.parentId = entity.getParentId();
        this.content = entity.getContent();
        this.deleteYn = entity.getDeleteYn();
        this.createDate = entity.getCreateDate();
    }
}
