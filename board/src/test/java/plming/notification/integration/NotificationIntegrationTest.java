//package plming.notification.integration;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.ServiceIntegrationTest;
//import plming.comment.dto.CommentRequestDto;
//import plming.notification.dto.NotificationDto;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class NotificationIntegrationTest extends ServiceIntegrationTest {
//
//    @Test
//    @DisplayName("게시글 신청 알림 보내기")
//    void send() {
//
//        // given
//        applicationService.save(study.getId(), studyApplyMember.getId());
//
//        // when
//        List<NotificationDto> notifications = notificationService.findAllNotifications(study.getUser().getId());
//
//        // then
//        assertEquals(1, notifications.size());
//    }
//
//    @Test
//    @DisplayName("게시글 승인, 거절 알림 보내기")
//    void updateStatus() {
//
//        // given
//        applicationService.save(study.getId(), studyApplyMember.getId());
//        applicationService.save(study.getId(), generalMember.getId());
//        applicationService.updateAppliedStatus(study.getId(), studyApplyMember.getNickname(), "승인");
//        applicationService.updateAppliedStatus(study.getId(), generalMember.getNickname(), "거절");
//
//        // when
//        List<NotificationDto> notifications1 = notificationService.findAllNotifications(study.getUser().getId());
//        List<NotificationDto> notifications2 = notificationService.findAllNotifications(studyApplyMember.getId());
//        List<NotificationDto> notifications3 = notificationService.findAllNotifications(generalMember.getId());
//
//        // then
//        assertEquals(2, notifications1.size());
//        assertEquals(1, notifications2.size());
//        assertEquals(1, notifications3.size());
//    }
//
//    @Test
//    @DisplayName("댓글, 대댓글 알림 보내기")
//    void sendCommentNotification() {
//
//        // given
//        CommentRequestDto commentRequestDto1 = new CommentRequestDto("첫 번째 댓글", null);
//        Long comment1Id = commentService.registerComment(study.getId(), generalMember.getId(), commentRequestDto1);
//
//        CommentRequestDto commentRequestDto2 = new CommentRequestDto("첫 번째 댓글의 대댓글", comment1Id);
//        commentService.registerComment(study.getId(), studyApplyMember.getId(), commentRequestDto2);
//
//        // when
//        List<NotificationDto> notifications1 = notificationService.findAllNotifications(study.getUser().getId());
//        List<NotificationDto> notifications2 = notificationService.findAllNotifications(generalMember.getId());
//        List<NotificationDto> notifications3 = notificationService.findAllNotifications(studyApplyMember.getId());
//
//        // then
//        assertEquals(1, notifications1.size());
//        assertEquals(1, notifications2.size());
//        assertEquals(0, notifications3.size());
//    }
//}
