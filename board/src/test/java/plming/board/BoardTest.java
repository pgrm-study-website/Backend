package plming.board;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.user.entity.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @AfterEach
    void afterEach() {
        boardRepository.deleteAll();
    }

    @Test
    void save() {
        // given
        Board post = Board.builder()
                .title("게시글 생성 테스트 1")
                .category("스터디")
                .content("CRUD 테스트 입니다.")
                .period("하루")
                .status("모집 중")
                .user(User.builder()
                        .social(2)
                        .role("ROLE_USER")
                        .password("api")
                        .nickname("api")
                        .introduce("api test")
                        .image("no image")
                        .github("api")
                        .email("api@email.com")
                        .build())
                .build();

        // when
        boardRepository.save(post);

        // then
        Board postCp = boardRepository.findById(post.getId()).get();
        assertThat(postCp.getTitle()).isEqualTo(post.getTitle());
        assertThat(postCp.getContent()).isEqualTo(post.getContent());
        assertThat(postCp.getUser()).isEqualTo(post.getUser());
    }

    @Test
    void findAll() {
        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        assertEquals(boardRepository.count(), boardList.size());
    }

    @Test
    void delete() {
        // given
        Board post = boardRepository.findById(22L).get();

        // when
        boardRepository.delete(post);

        // then
        assertEquals(2, boardRepository.count());
    }
}
