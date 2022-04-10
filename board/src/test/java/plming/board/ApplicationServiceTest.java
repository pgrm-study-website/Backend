//package plming.board;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.board.entity.ApplicationRepository;
//import plming.board.entity.Board;
//import plming.board.entity.BoardRepository;
//import plming.board.service.BoardService;
//import plming.user.dto.UserListResponseDto;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class ApplicationServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ApplicationRepository applicationRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private BoardService boardService;
//
//    private User user1;
//    private User user2;
//    private Board post1;
//    private Board post2;
//
//    @BeforeEach
//    void beforeEach() {
//        user1 = User.builder()
//                .nickname("nickname1")
//                .email("email1@gmail.com")
//                .role("ROLE_USER")
//                .social(0)
//                .build();
//        user2 = User.builder()
//                .nickname("nickname1")
//                .email("email@gmail1.com")
//                .role("ROLE_USER")
//                .social(0)
//                .build();
//
//        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
//                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
//                .build();
//        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
//                .period(1).category("프로젝트").status("모집 중").title("사용자2의 게시글 1")
//                .build();
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        boardRepository.save(post1);
//        boardRepository.save(post2);
//
//    }
//
//    @AfterEach
//    void afterEach() {
//        applicationRepository.deleteAll();
//        boardRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("게시글 신청")
//    void save() {
//
//        // when (신청 성공)
//        String board1Id = boardService.apply(post1.getId(), user2.getId());
//        String board2Id = boardService.apply(post2.getId(), user1.getId());
//
//        // when (신청 실패)
//        String board3Id = boardService.apply(post1.getId(), user2.getId());
//        String board4Id = boardService.apply(post1.getId(), user2.getId());
//
//        // then (신청 성공)
//        assertEquals(post1.getId().toString(), board1Id);
//        assertEquals(post2.getId().toString(), board2Id);
//
//        // then (신청 실패)
//        assertEquals("신청", board3Id);
//        assertEquals("신청", board4Id);
//    }
//
////    @Test
////    @DisplayName("사용자가 신청한 게시글 조회")
////    void findAppliedBoardByUserId() {
////
////        // given
////        boardService.apply(post1.getId(), user2.getId());
////        boardService.apply(post2.getId(), user1.getId());
////
////        // when
////        List<BoardListResponseDto> appliedBoards = boardService.findAppliedBoardByUserId(user1.getId());
////
////        // then
////        assertEquals(1, appliedBoards.size());
////    }
//
//    @Test
//    @DisplayName("게시글 신청한 사용자 리스트 조회")
//    void findAppliedUserIdByBoardId() {
//
//        // given
//        boardService.apply(post1.getId(), user2.getId());
//        boardService.apply(post2.getId(), user1.getId());
//
//        // when
//        List<Map<String, Object>> appliedUsers = boardService.findAppliedUserByBoardId(post1.getId());
//
//        // then
//        assertEquals(appliedUsers.get(0).get("user").getClass(), UserListResponseDto.class);
//    }
//
////    @Test
////    @DisplayName("지원 상태 업데이트")
////    void update() {
////        // given
////        boardService.apply(post1.getId(), user2.getId());
////        boardService.apply(post2.getId(), user1.getId());
////
////        // when
////        String status1 = boardService.updateAppliedStatus(post1.getId(), user2.getId(), "승인");
////        String status2 = boardService.updateAppliedStatus(post2.getId(), user1.getId(), "거절");
////
////        // then
////        assertEquals("승인", status1);
////        assertEquals("거절", status2);
////    }
//
////    @Test
////    @DisplayName("참여자 수 계산")
////    void findParticipateNum() {
////        // given
////        boardService.apply(post1.getId(), user2.getId());
////        boardService.apply(post2.getId(), user1.getId());
////        boardService.updateAppliedStatus(post1.getId(), user2.getId(), "승인");
////        boardService.updateAppliedStatus(post2.getId(), user1.getId(), "거절");
////
////        // when
////        Integer post1ParticipateNum = boardService.countParticipantNum(post1.getId());
////        Integer post2ParticipateNum = boardService.countParticipantNum(post2.getId());
////
////        // then
////        assertEquals(1, post1ParticipateNum);
////        assertEquals(0, post2ParticipateNum);
////    }
//
//    @Test
//    @DisplayName("신청 취소하기")
//    void cancelApplied() {
//
//        // given
//        boardService.apply(post1.getId(), user2.getId());
//        boardService.apply(post2.getId(), user1.getId());
//        long count = applicationRepository.count();
//
//        // when
//        boardService.cancelApplied(post1.getId(), user2.getId());
//
//        // then
//        assertEquals(count - 1, applicationRepository.count());
//    }
//}