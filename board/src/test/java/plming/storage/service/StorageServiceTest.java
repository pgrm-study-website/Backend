//package plming.storage.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//class StorageServiceTest {
//
//    @Autowired
//    private StorageService storageService;
//
//    @Test
//    void urlToFilePath() {
//        String filePath = storageService.urlToFilePath("http://localhost:8080/test.jpg");
//        assertThat(filePath).isEqualTo("../image/test.jpg");
//    }
//}