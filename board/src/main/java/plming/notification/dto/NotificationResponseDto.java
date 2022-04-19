package plming.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private String content;
    private String url;

    public static NotificationResponseDto create(NotificationDto notificationDto) {
        return new NotificationResponseDto(notificationDto.getId(), notificationDto.getContent()
                , notificationDto.getUrl());
    }
}
