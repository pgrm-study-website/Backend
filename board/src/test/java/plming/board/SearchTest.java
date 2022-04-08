package plming.board;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.entity.BoardTagRepository;
import plming.board.service.BoardTagService;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SearchTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardTagService boardTagService;

    @Autowired
    private BoardTagRepository boardTagRepository;

    private User user1;
    private User user2;
    private Board post1;
    private Board post2;
    private Board post3;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder()
                .nickname("nickname1")
                .email("email1@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();
        user2 = User.builder()
                .nickname("nickname1")
                .email("email@gmail1.com")
                .role("ROLE_USER")
                .social(0)
                .build();

        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
                .participantMax(5)
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period(1).category("프로젝트").status("모집 중").title("user2의 게시글1")
                .participantMax(3)
                .build();

        post3 = Board.builder().user(user1).content("사용자1의 두 번째 게시글")
                .period(3).category("프로젝트").status("모집 중").title("사용자1의 게시글2")
                .participantMax(2)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        boardRepository.save(post1);
        boardRepository.save(post2);
        boardRepository.save(post3);
    }

    @AfterEach
    void afterEach() {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

//    @Test
//    @DisplayName("제목 검색하기")
//    void searchTitle() {
//
//        // when
//        List<Board> result = boardRepository.searchTitle("사용자1");
//
//        // then
//        assertEquals(2, result.size());
//    }
//
//    @Test
//    @DisplayName("내용 검색하기")
//    void searchContent() {
//
//        // when
//        List<Board> result = boardRepository.searchContent("게시글입니다.");
//
//        // then
//        assertEquals(2, result.size());
//
//    }
//
//    @Test
//    @DisplayName("카테고리로 검색하기")
//    void searchCategory() {
//
//        // when
//        List<Board> result1 = boardRepository.searchCategory(List.of("스터디"));
//        List<Board> result2 = boardRepository.searchCategory(List.of("프로젝트", "공모전"));
//        List<Board> result3 = boardRepository.searchCategory(List.of("스터디", "프로젝트", "공모전"));
//        List<Board> result4 = boardRepository.searchCategory(List.of("기타"));
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//        assertEquals(3, result3.size());
//        assertEquals(0, result4.size());
//
//    }
//
//    @Test
//    @DisplayName("태그로 검색하기")
//    void searchTag() {
//
//        // given
//        Long[] post1TagIds = {10L, 20L};
//        Long[] post2TagIds = {40L, 50L, 60L};
//        Long[] post3TagIds = {40L, 20L};
//        boardTagService.save(List.of(post1TagIds), post1);
//        boardTagService.save(List.of(post2TagIds), post2);
//        boardTagService.save(List.of(post3TagIds), post3);
//
//        // when
//        List<Board> result1 = boardRepository.searchTag(List.of(10));
//        List<Board> result2 = boardRepository.searchTag(List.of(40, 50));
//        List<Board> result3 = boardRepository.searchTag(List.of(10, 40));
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//        assertEquals(3, result3.size());
//
//        boardTagRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("제목+내용 검색하기")
//    void searchTitleAndContent() {
//
//        // when
//        List<Board> result = boardRepository.searchTitle("user");
//
//        // then
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    @DisplayName("제목+카테고리 검색하기")
//    void searchTitleAndCategory() {
//
//        // when
//        List<Board> result1 = boardRepository.searchTitleAndCategory("사용자1", List.of("스터디"));
//        List<Board> result2 = boardRepository.searchTitleAndCategory("사용자1", List.of("스터디", "프로젝트"));
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//    }
//
//    @Test
//    @DisplayName("제목+태그 검색하기")
//    void searchTitleAndTag() {
//
//        // given
//        Long[] post1TagIds = {10L, 20L};
//        Long[] post2TagIds = {40L, 50L, 60L};
//        Long[] post3TagIds = {40L, 20L};
//        boardTagService.save(List.of(post1TagIds), post1);
//        boardTagService.save(List.of(post2TagIds), post2);
//        boardTagService.save(List.of(post3TagIds), post3);
//
//        // when
//        List<Board> result1 = boardRepository.searchTitleAndTag("사용자1", List.of(10));
//        List<Board> result2 = boardRepository.searchTitleAndTag("사용자", List.of(10, 40, 50));
//        List<Board> result3 = boardRepository.searchTitleAndTag("사용자", null);
//
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//        assertEquals(2, result3.size());
//    }
//
//    @Test
//    @DisplayName("내용+카테고리 검색하기")
//    void searchContentAndCategory() {
//
//        // when
//        List<Board> result1 = boardRepository.searchContentAndCategory("사용자1", List.of("스터디"));
//        List<Board> result2 = boardRepository.searchContentAndCategory("사용자1", List.of("스터디", "프로젝트"));
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//    }
//
//    @Test
//    @DisplayName("내용+태그 검색하기")
//    void searchContentAndTag() {
//
//        // given
//        Long[] post1TagIds = {10L, 20L};
//        Long[] post2TagIds = {40L, 50L, 60L};
//        Long[] post3TagIds = {40L, 20L};
//        boardTagService.save(List.of(post1TagIds), post1);
//        boardTagService.save(List.of(post2TagIds), post2);
//        boardTagService.save(List.of(post3TagIds), post3);
//
//        // when
//        List<Board> result1 = boardRepository.searchContentAndTag("사용자1", List.of(10));
//        List<Board> result2 = boardRepository.searchContentAndTag("사용자", List.of(40, 50));
//        List<Board> result3 = boardRepository.searchContentAndTag("사용자", null);
//
//
//        // then
//        assertEquals(1, result1.size());
//        assertEquals(2, result2.size());
//        assertEquals(3, result3.size());
//    }
}
