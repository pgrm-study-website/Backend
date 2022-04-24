package plming.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import plming.notification.entity.Notification;
import plming.notification.entity.NotificationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    private String content;
    private String url;
    private NotificationType type;
    private LocalDateTime createDate;

    public static NotificationDto create(Notification notification) {
        return new NotificationDto(notification.getId(), notification.getContent(),
                notification.getUrl(), notification.getNotificationType(), notification.getCreateDate());
    }
}
