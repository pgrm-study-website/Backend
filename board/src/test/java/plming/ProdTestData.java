//package plming;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import javax.annotation.PostConstruct;
//
//
//@Component
//@RequiredArgsConstructor
//@Profile("dev")
//public class ProdTestData {
//
//    private InitService initService;
//
//    @PostConstruct
//    void init() {
//        initService.initMember();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final UserRepository userRepository;
//
//        private void initMember() {
//            User userA = User.builder().nickname("nicknameA").email("emailA@gmail.com").role("ROLE_USER").social(0).build();
//            userRepository.save(userA);
//        }
//    }
//}
