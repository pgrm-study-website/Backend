//package plming;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import plming.board.entity.*;
//import plming.board.service.ApplicationService;
//import plming.comment.entity.CommentRepository;
//import plming.notification.repository.NotificationRepository;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Profile("local")
//public class TestData {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.initMember();
//        initService.initBoard();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//
//        private final UserRepository userRepository;
//        private final BoardRepository boardRepository;
//        private final ApplicationRepository applicationRepository;
//
//        private void initMember() {
//            User userA = User.builder().nickname("nicknameA").email("emailA@gmail.com").role("ROLE_USER").social(0).build();
//            userRepository.save(userA);
//
//            User userB = User.builder().nickname("nicknameB").email("emailB@gmail.com").role("ROLE_USER").social(0).build();
//            userRepository.save(userB);
//        }
//
//        private void initBoard() {
//            User userA = User.builder().nickname("nicknameA").email("emailA@gmail.com").role("ROLE_USER").social(0).build();
//            userRepository.save(userA);
//
//            User userB = User.builder().nickname("nicknameB").email("emailB@gmail.com").role("ROLE_USER").social(0).build();
//            userRepository.save(userB);
//
//            Board post = Board.builder().user(userA).content("사용자1의 첫 번째 게시글입니다.")
//                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
//                .participantMax(5)
//                .build();
//            makeTestDummyPost(userA);
//
//            Application application = Application.builder()
//                    .board(boardRepository.getById(post.getId()))
//                    .user(userRepository.getById(userB.getId()))
//                    .status("대기")
//                    .build();
//            applicationRepository.save(application);
//        }
//
//        private void makeTestDummyPost(User user) {
//            for (int i = 0; i < 50; i++) {
//                if (i % 2 == 0) {
//                    Board board = Board.builder().user(user).content("사용자1의 첫 번째 게시글입니다.")
//                            .period(i % 3).category("스터디").status("모집 중").title("사용자1의 게시글"+i)
//                            .participantMax(i % 4)
//                            .build();
//                    boardRepository.save(board);
//
//                } else {
//                    Board board = Board.builder().user(user).content("사용자1의 첫 번째 게시글입니다.")
//                            .period(i % 3).category("프로젝트").status("모집 중").title("사용자1의 게시글"+i)
//                            .participantMax(i % 4)
//                            .build();
//                    boardRepository.save(board);
//                }
//            }
//        }
//    }
//}
