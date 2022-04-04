package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.service.BoardService;
import plming.user.dto.UserResponseDto;

import java.util.List;

@RestController
@RequestMapping("/posts")
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
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public Long update(@PathVariable final Long id, @RequestBody final BoardRequestDto post) {

        return boardService.update(id, post);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable final Long id) {

        return boardService.delete(id);
    }

    /**
     * 게시글 리스트 조회
     */
    @GetMapping
    public List<BoardResponseDto> findAll(@RequestParam final char deleteYn) {

        return boardService.findAllByDeleteYn(deleteYn);
    }

    /**
     * 게시글 리스트 조회 - 사용자 ID 기준
     */
    @GetMapping("/user")
    public List<BoardResponseDto> findAllByUserId(@RequestParam final Long userId) {

        return boardService.findAllByUserId(userId);
    }

    /**
     * 게시글 상세정보 조회
     */
    @GetMapping("/{id}")
    public BoardResponseDto findById(@PathVariable final Long id) {

        return boardService.findById(id);
    }

    /**
     * 게시글 신청
     */
    @PostMapping("/{id}/application")
    public Long apply(@PathVariable final Long id, @RequestParam final Long userId) {

        return boardService.apply(id, userId);
    }

    /**
     * 신청 게시글 리스트 조회 - 사용자 ID 기준 (User 부분으로 옮겨져야 할 것 같음)
     */
    @GetMapping("/application")
    public List<BoardResponseDto> findAppliedBoardByUserID(@RequestParam final Long userId) {

        return boardService.findAppliedBoardByUserId(userId);
    }

    /**
     * 신청 사용자 리스트 조회 - 게시글 ID 기준
     */
    @GetMapping("/{id}/application")
    public List<UserResponseDto> findAppliedUserByBoardId(@PathVariable final Long id) {

        return boardService.findAppliedUserByBoardId(id);
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    @PatchMapping("/{id}/application")
    public String updateAppliedStatus(@PathVariable final Long id, @RequestParam final Long userId, @RequestParam final String status) {

        return boardService.updateAppliedStatus(id, userId, status);
    }

}
