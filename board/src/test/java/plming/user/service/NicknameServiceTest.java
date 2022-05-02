//package plming.user.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//@SpringBootTest
//class NicknameServiceTest {
//
//    @Autowired
//    private NicknameService nicknameService;
//
//    @Test
//    void createRandomNicknameTest(){
//        String googleNickname = nicknameService.createRandomNickname(1);
//        String kakaoNickname = nicknameService.createRandomNickname(2);
//        String githubNickname = nicknameService.createRandomNickname(3);
//        System.out.println(googleNickname);
//        System.out.println(kakaoNickname);
//        System.out.println(githubNickname);
//        Assertions.assertTrue(googleNickname.contains("구글"));
//        Assertions.assertTrue(kakaoNickname.contains("카카오"));
//        Assertions.assertTrue(githubNickname.contains("깃허브"));
//    }
//
//}