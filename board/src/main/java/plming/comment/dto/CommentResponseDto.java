package plming.comment.dto;

import lombok.Builder;
import lombok.Getter;
import plming.comment.entity.Comment;
import plming.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {

    private Long id;
    private Long userId;
    private Long parentId;
    private String content;
    private LocalDateTime createDate;
    private List<RecommentResponseDto> recomment;
    private Long recommentSize;

    @Builder
    public CommentResponseDto(Comment entity, User user, List<RecommentResponseDto> recomment, Long recommentSize) {
        this.id = entity.getId();
        this.userId = user.getId();
        this.parentId = entity.getParentId();
        this.content = entity.getContent();
        this.createDate = entity.getCreateDate();
        this.recomment = recomment;
        this.recommentSize = recommentSize;
    }
}
