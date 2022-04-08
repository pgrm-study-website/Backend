package plming.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.service.BoardService;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.*;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    private Board post1;
    private Board post2;
    private User user1;
    private User user2;

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

        post1 = Board.builder().user(user1).content("user1의 첫 번째 게시글입니다.")
                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
                .participantMax(5)
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period(1).category("프로젝트").status("모집 중").title("user2의 게시글 1")
                .participantMax(3)
                .build();

//        userRepository.save(user1);
//        userRepository.save(user2);
//        boardRepository.save(post1);
//        boardRepository.save(post2);

    }

    @AfterEach
    void afterEach() {

        //boardRepository.deleteAll();
        //userRepository.deleteAll();
    }


    @Test
    @DisplayName("게시글 생성")
    public void save() {

        // given
        userRepository.save(user1);
        userRepository.save(user2);

        // when
        boardRepository.save(post1);
        boardRepository.save(post2);
    }

//    @Test
//    @DisplayName("게시글 수정")
//    void update() {
//
//        // given
//        Board board = new Board(user1, "스터디", "모집 완료", "1주일", "1번 게시글 수정", "1번 게시글 수정합니다.");
//
//        // when
//        post1.update("1번 게시글 수정", "1번 게시글 수정합니다.", "스터디", "모집 완료", "1주일");
//
//        // then
//        assertEquals(board.getTitle(), post1.getTitle());
//        assertEquals(board.getContent(), post1.getContent());
//        assertEquals(board.getStatus(), post1.getStatus());
//        assertEquals(board.getCategory(), post1.getCategory());
//        assertEquals(board.getPeriod(), post1.getPeriod());
//    }


    @Test
    @DisplayName("게시글 삭제 - 실제 DB에서 삭제)")
    void delete() {

        // when
        boardRepository.deleteById(post1.getId());

        // then
        assertEquals(1, boardRepository.count());
    }

    @Test
    @DisplayName("게시글 삭제 - deleteYn 값 변경")
    void deleteYn() {

        // when
        post1.delete();

        // then
        assertEquals('1', post1.getDeleteYn());
    }

    @Test
    @DisplayName("게시글 리스트 조회")
    void findAll() {

        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        assertEquals(boardRepository.count(), boardList.size());
    }

    @Test
    @DisplayName("게시글 리스트 조회 - 삭제 여부 기준")
    public void findAllByDeleteYn() {

        // when
        boardService.delete(post1.getId());
        List<Board> boardList = boardRepository.findAllByDeleteYn('0', Sort.by(DESC, "id", "createDate"));

        // then
        assertEquals(1, boardList.size());
    }

    @Test
    @DisplayName("게시글 리스트 조회 - 사용자 id 기준")
    public void findAllByUserId() {

        // when
        List<Board> boardList = boardRepository.findAllByUserId(user1.getId(), Sort.by(DESC, "id", "createDate"));

        // then
        assertEquals(1, boardList.size());

    }

    @Test
    @DisplayName("게시글 상세 정보 조회")
    public void findById() {

        // when
        BoardResponseDto board1 = boardService.findById(post1.getId());
        BoardResponseDto board2 = boardService.findById(post2.getId());

        // then
        assertEquals(post1.getTitle(), board1.getTitle());
        assertEquals(post2.getContent(), board2.getContent());

        // then
        assertThat(board1.getTitle().equals(post2.getTitle())).isFalse();
        assertThat(board2.getContent().equals(post1.getContent())).isFalse();
    }

}