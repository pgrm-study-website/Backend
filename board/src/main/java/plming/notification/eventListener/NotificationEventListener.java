package plming.notification.eventListener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import plming.event.ApplicationCreateEvent;
import plming.event.CommentCreateEvent;
import plming.event.MessageCreateEvent;
import plming.notification.service.NotificationService;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleApplicationCreateEvent(ApplicationCreateEvent event) {

        notificationService.send(event.getReceiver(), event.getNotificationType(),
                event.getNotificationType().makeContent(event.getApplication().getBoard().getTitle()),
                event.getNotificationType().makeUrl(event.getApplication().getBoard().getId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleCommentCreateEvent(CommentCreateEvent event) {

        notificationService.send(event.getReceiver(), event.getNotificationType(),
                event.getNotificationType().makeContent(event.getComment().getBoard().getTitle()),
                event.getNotificationType().makeUrl(event.getComment().getBoard().getId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void handleMessageCreateEvent(MessageCreateEvent event) {

        notificationService.send(event.getReceiver(), event.getNotificationType(),
                event.getNotificationType().makeContent(event.getMessage().getSender().getNickname()),
                event.getNotificationType().makeUrl(event.getReceiver().getId()));
    }
}
