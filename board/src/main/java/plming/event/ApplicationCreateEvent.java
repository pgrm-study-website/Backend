package plming.event;

import lombok.Getter;
import plming.board.boardApply.entity.Application;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;

@Getter
public class ApplicationCreateEvent {

    private final Application application;
    private final NotificationType notificationType;
    private final User receiver;

    public ApplicationCreateEvent(Application application, User receiver, NotificationType notificationType) {
        this.application = application;
        this.receiver = receiver;
        this.notificationType = notificationType;
    }
}
