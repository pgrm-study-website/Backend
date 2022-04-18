//package plming.notification.entity;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import plming.notification.exception.InvalidRelatedURLException;
//
//public class RelatedURLTest {
//
//    @Test
//    @DisplayName("관련 링크가 공백이 아닐 경우 성공")
//    void test1() throws Exception {
//
//        Assertions.assertDoesNotThrow(() -> new RelatedURL("plming.kr/posts"));
//    }
//
//    @Test
//    @DisplayName("관련 링크가 공백일 경우 실패")
//    void test2() throws Exception {
//
//        Assertions.assertThrows(InvalidRelatedURLException.class, () -> new RelatedURL(null));
//    }
//}
