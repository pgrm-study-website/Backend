package plming.event;

import lombok.Getter;
import plming.board.boardComment.entity.Comment;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;

@Getter
public class CommentCreateEvent {

    private final Comment comment;
    private final NotificationType notificationType;
    private final User receiver;

    public CommentCreateEvent(Comment comment, User receiver, NotificationType notificationType) {
        this.comment = comment;
        this.receiver = receiver;
        this.notificationType = notificationType;
    }
}
