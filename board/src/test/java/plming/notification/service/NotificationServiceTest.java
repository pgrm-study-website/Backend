//package plming.notification.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.common.ServiceIntegrationTest;
//import plming.notification.dto.NotificationDto;
//import plming.notification.entity.NotificationType;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class NotificationServiceTest extends ServiceIntegrationTest {
//
//    @Test
//    @DisplayName("알림 구독")
//    void subscribe() throws Exception {
//
//        // given
//        String lastEventId = "";
//
//        // when, then
//        assertDoesNotThrow(() -> notificationService.subscribe(generalMember.getId(), lastEventId));
//    }
//
//    @Test
//    @DisplayName("알림 메시지 전송")
//    void send() throws Exception {
//
//        // given
//        String lastEventId = "";
//        notificationService.subscribe(generalMember.getId(), lastEventId);
//
//        // when, then
//        assertDoesNotThrow(() -> notificationService.send(generalMember, NotificationType.apply, "게시글에 참여 신청했습니다.", "localhost:8080/posts/1"));
//
//    }
//
//    @Test
//    @DisplayName("모든 알림 메시지 읽기")
//    void findAllNotifications() throws Exception {
//
//        // given
//        notificationService.send(generalMember, NotificationType.apply, "게시글에 참여 신청했습니다.", "localhost:8080/posts/1");
//
//        // when
//        List<NotificationDto> notifications = notificationService.findAllNotifications(generalMember.getId());
//
//        // then
//        assertEquals(1, notifications.size());
//    }
//
//    @Test
//    @DisplayName("읽지 않은 메시지 개수를 조회")
//    void countUnReadNotifications() throws Exception {
//
//        // given
//        notificationService.send(generalMember, NotificationType.apply, "게시글에 참여 신청했습니다.", "localhost:8080/posts/1");
//
//        // when
//        Long count = notificationService.countUnReadNotifications(generalMember.getId());
//
//        // then
//        assertEquals(1, count);
//    }
//
//    @Test
//    @DisplayName("메시지를 조회하면 메시지를 읽게 된다")
//    void countUnReadNotifications2() throws Exception {
//
//        // when
//        notificationService.findAllNotifications(generalMember.getId());
//        persistenceContextClear();
//
//        Long count = notificationService.countUnReadNotifications(studyApplyMember.getId());
//
//        // then
//        assertEquals(0, count);
//    }
//
//    private void persistenceContextClear() {
//        em.flush();
//        em.clear();
//    }
//
//    @Test
//    @DisplayName("알림 하나 삭제")
//    void deleteNotification() throws Exception {
//
//        // given
//        notificationService.send(generalMember, NotificationType.apply, "게시글에 참여 신청했습니다.", "localhost:8080/posts/1");
//        List<NotificationDto> notifications = notificationService.findAllNotifications(generalMember.getId());
//        Long notificationId = notifications.get(0).getId();
//
//        // when
//        notificationService.deleteNotification(notificationId);
//
//        // then
//        assertEquals(0, notificationService.findAllNotifications(generalMember.getId()).size());
//    }
//
//    @Test
//    @DisplayName("알림 하나 삭제")
//    void deleteAllByUserId() throws Exception {
//
//        // given
//        notificationService.send(generalMember, NotificationType.apply, "게시글에 참여 신청했습니다.", "localhost:8080/posts/1");
//        List<NotificationDto> notifications1 = notificationService.findAllNotifications(generalMember.getId());
//
//        // when
//        notificationService.deleteAllByUserId(generalMember.getId());
//        List<NotificationDto> notifications2 = notificationService.findAllNotifications(generalMember.getId());
//
//        // then
//        assertEquals(1, notifications1.size());
//        assertEquals(0, notifications2.size());
//    }
//
//
//
//}
