package plming.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.model.ApplicationService;
import plming.board.model.BoardService;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ApplicationService applicationService;

    private User user1;
    private User user2;
    private Board post1;
    private Board post2;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder().email("email@email.com").github("github")
                .image("no image").introduce("introduce").nickname("nickname")
                .password("password").role("ROLE_USER").social(1)
                .build();
        user2 = User.builder().email("email2@email.com").github("github2")
                .image("no image").introduce("introduce2").nickname("nickname2")
                .password("password2").role("ROLE_ADMIN").social(1)
                .build();
        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
                .period("1개월").category("스터디").status("모집 중").title("사용자1의 게시글1")
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period("1개월").category("프로젝트").status("모집 중").title("사용자2의 게시글 1")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        boardRepository.save(post1);
        boardRepository.save(post2);

    }

    @AfterEach
    void afterEach() {
        applicationRepository.deleteAll();
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 신청")
    void save() {

        // when
        Long board1Id = boardService.apply(post1.getId(), user2.getId());
        Long board2Id = boardService.apply(post2.getId(), user1.getId());

        // then
        assertEquals(post1.getId(), board1Id);
        assertEquals(post2.getId(), board2Id);
    }

    @Test
    @DisplayName("사용자가 신청한 게시글 조회")
    void findAppliedBoardByUserId() {

        // given
        boardService.apply(post1.getId(), user2.getId());
        boardService.apply(post2.getId(), user1.getId());

        // when
        List<BoardResponseDto> appliedBoards = boardService.findAppliedBoardByUserId(user1.getId());

        // then
        assertEquals(1, appliedBoards.size());
    }

    @Test
    @DisplayName("게시글 ID 기준 신청한 사용자 리스트 조회")
    void findAppliedUserIdByBoardId() {

        // given
        boardService.apply(post1.getId(), user2.getId());
        boardService.apply(post2.getId(), user1.getId());

        // when

        // then
    }

//    @Test
//    @DisplayName("지원 상태 업데이트")
//    void update() {
//        // given
//        boardService.apply(post1.getId(), user2.getId());
//        boardService.apply(post2.getId(), user1.getId());
//
//        // when
//        boardService.updateAppliedStatus(post1.getId(), user2.getId());
//        boardService.updateAppliedStatus(post2.getId(), user1.getId());
//
//        // then
//        boardService.findAppliedBoardByUserId(user2.getId()).stream().map(BoardResponseDto::getId)
//                .allMatch(boardId -> post1.getId() == boardId);
//    }
}