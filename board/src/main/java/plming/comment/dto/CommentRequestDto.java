package plming.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.entity.Board;
import plming.comment.entity.Comment;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CommentRequestDto {

    private String content;
    private Long parentId;

    public Comment toEntity(Board board, User user) {
        return Comment.builder()
                .board(board)
                .user(user)
                .parentId(parentId)
                .content(content)
                .build();
    }
}
