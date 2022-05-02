package plming.board.boardComment.dto;

import lombok.Builder;
import lombok.Getter;
import plming.board.boardComment.entity.Comment;
import plming.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class RecommentResponseDto {

    private final Long id;
    private final Long userId;
    private final Long parentId;
    private final String content;
    private final LocalDateTime createDate;
    private final char deleteYn;

    @Builder
    public RecommentResponseDto(Comment entity, User user) {
        this.id = entity.getId();
        this.userId = user.getId();
        this.parentId = entity.getParentId();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.deleteYn = entity.getDeleteYn();
    }
}