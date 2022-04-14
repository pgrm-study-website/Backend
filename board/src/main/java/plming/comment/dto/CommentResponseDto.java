package plming.comment.dto;

import lombok.Builder;
import lombok.Getter;
import plming.comment.entity.Comment;
import plming.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {

    private final Long id;
    private final Long userId;
    private final String content;
    private final LocalDateTime createDate;
    private final char deleteYn;
    private final List<RecommentResponseDto> recomment;
    private final Long recommentSize;

    @Builder
    public CommentResponseDto(Comment entity, User user, List<RecommentResponseDto> recomment, Long recommentSize) {
        this.id = entity.getId();
        this.userId = user.getId();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.deleteYn = entity.getDeleteYn();
        this.recomment = recomment;
        this.recommentSize = recommentSize;
    }
}