//package plming.notification.controller;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.notification.entity.NotificationType;
//
//import javax.servlet.http.Cookie;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class NotificationControllerTest extends ControllerIntegrationTest{
//
//    @Test
//    @DisplayName("SSE에 연결")
//    void subscribe() throws Exception {
//
//        // given
//        String accessToken = jwtTokenProvider.createToken(studyMember.getId());
//
//        // when, then
//        mockMvc.perform(get("/notifications/subscribe")
//                        .cookie(new Cookie("token", accessToken)))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("모든 알림 조회")
//    void findAllNotification() throws Exception {
//
//        // given
//        String accessToken = jwtTokenProvider.createToken(studyMember.getId());
//
//        // when
//        notificationService.send(studyMember, NotificationType.APPLY, "게시글에 참여를 신청했습니다.",
//                "localhost:8080/posts/59");
//
//        // then
//        mockMvc.perform(get("/notifications")
//                .cookie(new Cookie("token", accessToken)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.length()").value(1));
//    }
//
//    @Test
//    @DisplayName("안 읽은 모든 알림 개수를 조회")
//    void countUnReadNotifications() throws Exception {
//
//        // given
//        String accessToken = jwtTokenProvider.createToken(studyAdminMember.getId());
//
//        // when
//        notificationService.send(studyAdminMember, NotificationType.APPLY, "게시글에 참여를 신청한 사용자가 있습니다.",
//                "localhost:8080/posts/60");
//
//        // then
//        mockMvc.perform(get("/notifications/count")
//                        .cookie(new Cookie("token", accessToken)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(1L));
//    }
//
//    @Test
//    @DisplayName("알림을 조회하면 알림을 읽게 된다.")
//    void countUnReadNotifications2() throws Exception {
//
//        // given
//        String accessToken = jwtTokenProvider.createToken(studyMember.getId());
//        notificationService.send(studyMember, NotificationType.APPLY, "게시글에 참여를 신청한 사용자가 있습니다.",
//                "localhost:8080/posts/60");
//
//        // when
//        notificationService.findAllNotifications(studyMember.getId());
//
//        // then
//        mockMvc.perform(get("/notifications/count")
//                        .cookie(new Cookie("token", accessToken)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value(0L));
//    }
//}
