package plming.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDto {
    private User user;
    private NotificationType notificationType;
    private String content;
    private String url;
}
