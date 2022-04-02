package plming.board;

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

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    private Board board;

//    @AfterEach
//    void afterEach() {
//        applicationRepository.deleteAll();
//    }

    @Test
    void save() {
        // given
        Board board = boardRepository.getById(56L);

        // when
        Long boardId = applicationService.save(56L, 20L);

        // then
        assertEquals(board.getId(), boardId);
    }

    @Test
    void findByApplicationByUserId() {
        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Application> applications = applicationRepository.findAllByUserId(15L, sort);
        List<Board> boards = applications.stream().map(Application::getBoard).collect(Collectors.toList());
        List<BoardResponseDto> responseDtos = boardService.getTagName(boards);
        responseDtos.forEach(System.out::println);
    }
}