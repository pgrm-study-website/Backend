package plming.notification.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import plming.ServiceIntegrationTest;
import plming.notification.dto.NotificationResponseDto;
import plming.notification.entity.Notification;
import plming.notification.entity.NotificationType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationServiceTest extends ServiceIntegrationTest {

    @Test
    @DisplayName("알림 구독")
    void subscribe() throws Exception {

        // given
        String lastEventId = "";

        // when, then
        assertDoesNotThrow(() -> notificationService.subscribe(studyMember.getId(), lastEventId));
    }

    @Test
    @DisplayName("알림 메시지 전송")
    void send() throws Exception {

        // given
        String lastEventId = "";
        notificationService.subscribe(studyMember.getId(), lastEventId);

        // when, then
        assertDoesNotThrow(() -> notificationService.send(studyMember, NotificationType.APPLY, "게시글에 참여 신청했습니다.", "localhost:80/posts/1"));

    }

    @Test
    @DisplayName("모든 알림 메시지 읽기")
    void findAllNotifications() throws Exception {

        // when
        List<NotificationResponseDto> notifications = notificationService.findAllNotifications(studyApplyMember.getId());

        // then
        assertEquals(5, notifications.size());
    }

    @Test
    @DisplayName("읽지 않은 메시지 개수를 조회")
    void countUnReadNotifications() throws Exception {

        // when
        Long count = notificationService.countUnReadNotifications(studyApplyMember.getId());

        // then
        assertEquals(5, count);
    }

    @Test
    @DisplayName("메시지를 조회하면 메시지를 일게 된다")
    void countUnReadNotifications2() throws Exception {

        // when
        notificationService.findAllNotifications(studyApplyMember.getId());
        persistenceContextClear();

        Long count = notificationService.countUnReadNotifications(studyApplyMember.getId());

        // then
        assertEquals(0, count);
    }

    private void persistenceContextClear() {
        em.flush();
        em.clear();
    }
}
