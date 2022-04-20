package plming.event;

import lombok.Getter;
import plming.message.entity.Message;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;

@Getter
public class MessageCreateEvent {

    private final Message message;
    private final NotificationType notificationType;
    private final User receiver;

    public MessageCreateEvent(Message message, User receiver, NotificationType notificationType) {
        this.message = message;
        this.notificationType = notificationType;
        this.receiver = receiver;
    }
}
