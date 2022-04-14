package plming.comment.dto;

import lombok.Builder;
import lombok.Getter;
import plming.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentOneResponseDto {

    private final Long id;
    private final Long postId;
    private final Long parentId;
    private final String content;
    private final LocalDateTime createDate;

    @Builder
    public CommentOneResponseDto(Comment entity) {
        this.id = entity.getId();
        this.postId = entity.getBoard().getId();
        this.parentId = entity.getParentId();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
    }
}