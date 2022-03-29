package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.model.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    /**
     * 게시글 생성
     */
    @PostMapping
    public Long save(@RequestBody final BoardRequestDto post) {
        return boardService.save(post);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping
    public List<BoardResponseDto> findAll() {
        return boardService.findAll();
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public Long save(@PathVariable final Long id, @RequestBody final BoardRequestDto post) {
        return boardService.update(id, post);
    }
}
