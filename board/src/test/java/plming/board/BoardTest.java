package plming.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void save() {
        // given
        Board post = Board.builder()
                .title("1번 게시글 제목")
                .category("스터디")
                .content("CRUD 테스트 입니다.")
                .period("하루")
                .status("모집 중")
                .user("사용자1")
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
