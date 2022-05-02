package plming.board.boardComment.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.board.entity.Board;
import plming.board.boardComment.entity.Comment;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
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