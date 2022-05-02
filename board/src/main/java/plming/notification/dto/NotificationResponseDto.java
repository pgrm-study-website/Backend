package plming.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import plming.notification.entity.NotificationType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private String content;
    private String url;
    private NotificationType type;
    private LocalDateTime createDate;

    public static NotificationResponseDto create(NotificationDto notificationDto) {
        return new NotificationResponseDto(notificationDto.getId(), notificationDto.getContent()
                , notificationDto.getUrl(), notificationDto.getType(), notificationDto.getCreateDate());
    }
}
