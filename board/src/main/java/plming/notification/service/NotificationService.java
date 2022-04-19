package plming.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.notification.dto.NotificationDto;
import plming.notification.entity.Notification;
import plming.notification.entity.NotificationType;
import plming.notification.repository.EmitterRepository;
import plming.notification.repository.NotificationRepository;
import plming.user.entity.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${spring.sse.time}")
    private Long timeout;
    private final NotificationRepository notificationRepository;
    private final EmitterRepository emitterRepository;

    public SseEmitter subscribe(Long userId, String lastEventId) {

        String emitterId = makeTimeIncludeId(userId);
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

        // 503 에러를 방지하기 위한 더미 이벤트 저송
        String eventId = makeTimeIncludeId(userId);
        sendNotification(emitter, eventId, emitterId, "EventStream Created/ [userId=" + userId + "]");

        // 클라이언트가 미수신한 Event 목록이 존재할 경우 정송하여 Event 유실을 예방
        if(hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
        }

        return emitter;
    }

    public void send(User receiver, NotificationType notificationType, String content, String url) {

        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content, url));

        String receiverId = String.valueOf(receiver.getId());
        String eventId = receiverId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiverId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendNotification(emitter, eventId, key, NotificationDto.create(notification));
                }
        );
    }

    @Transactional
    public List<NotificationDto> findAllNotifications(Long userId) {

        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        notifications.stream().forEach(notification -> notification.read());

        return notifications.stream().map(NotificationDto::create).collect(Collectors.toList());
    }

    public Long countUnReadNotifications(Long userId) {

        return notificationRepository.countUnReadNotification(userId);
    }

    /**
     * 알림 하나 삭제
     */
    @Transactional
    public void deleteNotification(Long notificationId) {

        notificationRepository.deleteById(notificationId);
    }

    /**
     * 전체 알림 삭제
     */
    @Transactional
    public void deleteAllByUserId(Long userId) {

        notificationRepository.deleteAllByUserId(userId);
    }

    private String makeTimeIncludeId(Long userId) {

        return userId + "_" + System.currentTimeMillis();
    }

    private void sendNotification(SseEmitter emitter, String eventId, String emitterId, Object data) {

        try {
            emitter.send(SseEmitter.event().id(eventId).data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {

        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {

        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                .forEach(entry -> sendNotification(emitter, entry.getKey(), emitterId, entry.getValue()));
    }

    private Notification createNotification(User receiver, NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .isRead(false)
                .build();
    }

}
