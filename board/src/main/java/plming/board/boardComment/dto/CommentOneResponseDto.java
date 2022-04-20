package plming.board.boardComment.dto;

import lombok.Builder;
import lombok.Getter;
import plming.board.boardComment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentOneResponseDto {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final Long userId;
    private final String content;
    private final LocalDateTime createDate;

    @Builder
    public CommentOneResponseDto(Comment entity) {
        this.id = entity.getId();
        this.postId = entity.getBoard().getId();
        this.parentId = entity.getParentId();
        this.userId = entity.getUser().getId();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
    }
}