//package plming.board;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.board.dto.SearchRequestDto;
//import plming.board.entity.Board;
//import plming.board.entity.BoardRepository;
//import plming.board.entity.BoardTagRepository;
//import plming.board.service.BoardTagService;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class SearchTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BoardTagService boardTagService;
//
//    @Autowired
//    private BoardTagRepository boardTagRepository;
//
//    private User user1;
//    private User user2;
//    private Board post1;
//    private Board post2;
//    private Board post3;
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
//        post1 = Board.builder().user(user1).content("user1 첫 번째 게시글입니다.")
//                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
//                .participantMax(5)
//                .build();
//        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
//                .period(1).category("프로젝트").status("모집 중").title("user2의 게시글1")
//                .participantMax(3)
//                .build();
//
//        post3 = Board.builder().user(user1).content("사용자1의 두 번째 게시글")
//                .period(3).category("프로젝트").status("모집 중").title("사용자1의 게시글2")
//                .participantMax(2)
//                .build();
//
//        Long[] post1TagIds = {10L, 20L};
//        Long[] post2TagIds = {40L, 50L, 60L};
//        Long[] post3TagIds = {40L, 20L};
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        boardRepository.save(post1);
//        boardRepository.save(post2);
//        boardRepository.save(post3);
//
//        boardTagService.save(List.of(post1TagIds), post1);
//        boardTagService.save(List.of(post2TagIds), post2);
//        boardTagService.save(List.of(post3TagIds), post3);
//    }
//
//    @AfterEach
//    void afterEach() {
//        boardTagRepository.deleteAll();
//        boardRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("모든 조건 적용한 검색")
//    void searchAllCondition() {
////
////        // given
////        SearchRequestDto search = new SearchRequestDto();
////        search.setSearchType("제목+내용");
////        search.setKeyword("user");
////        search.setCategory(List.of("스터디"));
////        search.setStatus(List.of("모집 중"));
////        search.setTagIds(List.of(10, 40));
////        search.setPeriod(List.of(1, 2));
////        search.setParticipantMax(List.of(1, 5));
////
////        // when
////        List<Board> result = boardRepository.searchAllCondition(search);
////
////        // then
////        assertEquals(1, result.size());
//    }
//
//}
