package plming.board;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
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

        post1 = Board.builder().user(user1).content("사용자 1의 첫 번째 게시글입니다.")
                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
                .participantMax(5)
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period(1).category("프로젝트").status("모집 중").title("사용자2의 게시글1")
                .participantMax(3)
                .build();

        post3 = Board.builder().user(user1).content("user1의 두 번째 게시글")
                .period(3).category("공모전").status("모집 중").title("user1의 게시글2")
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

    @Test
    @DisplayName("제목 검색하기")
    void searchTitle() {

        // when
        List<Board> result = boardRepository.searchTitle("사용자");

        // then
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("내용 검색하기")
    void searchContent() {

        // when
        List<Board> result = boardRepository.searchContent("게시글입니다.");

        // then
        assertEquals(2, result.size());

    }
}
