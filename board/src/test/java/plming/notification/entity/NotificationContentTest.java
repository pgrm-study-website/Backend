//package plming.notification.entity;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.notification.exception.InvalidNotificationContentException;
//
//public class NotificationContentTest {
//
//    @Test
//    @DisplayName("알림 내용이 50자 이내일 경우 성공")
//    void test1() throws Exception {
//
//        Assertions.assertDoesNotThrow(() -> new NotificationContent("hi".repeat(2)));
//    }
//
//    @Test
//    @DisplayName("알림 내용이 50자 이상일 경우 실패")
//    void test2() throws Exception {
//
//        Assertions.assertThrows(InvalidNotificationContentException.class, () -> new NotificationContent("hi".repeat(31)));
//    }
//
//    @Test
//    @DisplayName("알림 내용이 공백일 경우 실패")
//    void test3() throws Exception {
//
//        Assertions.assertThrows(InvalidNotificationContentException.class, () -> new NotificationContent(" "));
//    }
//}
