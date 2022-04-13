//package plming.board;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.board.entity.Board;
//import plming.board.entity.BoardRepository;
//import plming.board.entity.BoardTag;
//import plming.board.entity.BoardTagRepository;
//import plming.tag.entity.Tag;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class BoardTagServiceTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BoardTagRepository boardTagRepository;
//
//    @Autowired
//    private plming.board.service.BoardTagService boardTagService;
//
//    private User user1;
//    private User user2;
//    private Board post1;
//    private Board post2;
//    private Long[] post1TagIds = {10L, 20L};
//    private Long[] post2TagIds = {40L, 50L, 60L};
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
//        post1 = Board.builder().user(user1).content("user1의 첫 번째 게시글입니다.")
//                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
//                .build();
//        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
//                .period(1).category("프로젝트").status("모집 중").title("user2의 게시글 1")
//                .build();
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        boardRepository.save(post1);
//        boardRepository.save(post2);
////        boardTagService.save(List.of(post1TagIds), post1);
////        boardTagService.save(List.of(post2TagIds), post2);
//
//    }
//
//    @AfterEach
//    void afterEach() {
////        boardTagRepository.deleteAll();
////        boardRepository.deleteAll();
////        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("태그 저장")
//    void save() {
//
//        // when
//        boardTagService.save(List.of(post1TagIds), post1);
//        boardTagService.save(List.of(post2TagIds), post2);
//    }
//
//    @Test
//    @DisplayName("태그 ID 리스트 조회 - 게시글 ID 기준")
//    void findAllByBoardId() {
//
//        // given
//        Long post1Id = post1.getId();
//        Long post2Id = post2.getId();
//
//        // when
//        List<BoardTag> post1Tags = boardTagRepository.findAllByBoardId(post1Id);
//        List<BoardTag> post2Tags = boardTagRepository.findAllByBoardId(post2Id);
//
//        // then
//        List<Long> post1TagIdCp = post1Tags.stream().map(BoardTag::getTag).map(Tag::getId).collect(Collectors.toList());
//        List<Long> post2TagIdCp = post2Tags.stream().map(BoardTag::getTag).map(Tag::getId).collect(Collectors.toList());
//
//        assertEquals(List.of(post1TagIds), post1TagIdCp);
//        assertEquals(List.of(post2TagIds), post2TagIdCp);
//    }
//
//    @Test
//    @DisplayName("태그 이름 리스트 조회 - 게시글 ID 기준")
//    void findTagNameByBoardId() {
//
//        // when
//        List<String> post1TagNames = boardTagService.findTagNameByBoardId(post1.getId());
//        List<String> post2TagNames = boardTagService.findTagNameByBoardId(post2.getId());
//
//        // then
//        assertEquals(post1TagIds.length, post1TagNames.size());
//        assertEquals(post2TagIds.length, post2TagNames.size());
//    }
//
//    @Test
//    @DisplayName("게시글 태그 삭제")
//    void deleteAllByBoardId() {
//
//        // when
//        boardTagRepository.deleteAllByBoardId(post1.getId());
//        boardTagRepository.deleteAllByBoardId(post2.getId());
//
//        // then
//        assertEquals(0, boardTagRepository.findAllByBoardId(post1.getId()).size());
//        assertEquals(0, boardTagRepository.findAllByBoardId(post2.getId()).size());
//
//    }
//
//}
