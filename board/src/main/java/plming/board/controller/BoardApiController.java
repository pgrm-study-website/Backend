package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.board.dto.BoardListResponseDto;
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
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 게시글 생성
     */
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody final BoardRequestDto post) {

        return ResponseEntity.status(201).body(boardService.save(post));
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
    public List<BoardListResponseDto> findAll() {

        return boardService.findAllByDeleteYn('0');
    }

    /**
     * 게시글 리스트 조회 - 사용자 ID 기준
     */
    @GetMapping("/user")
    public List<BoardListResponseDto> findAllByUserId(@CookieValue String token) {
        Long userId = jwtTokenProvider.getUserId(token);
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
    public ResponseEntity<String> apply(@PathVariable final Long id, @CookieValue final Long userId) {

        String appliedStatus = boardService.apply(id, userId);

        if (appliedStatus.equals("마감")) {
            return ResponseEntity.status(204).build();
        }
        if (appliedStatus.equals("신청")) {
            return ResponseEntity.status(204).build();
        }
        if (appliedStatus.equals("거절")) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(appliedStatus);
    }


    /**
     * 신청 게시글 리스트 조회 - 사용자 ID 기준 (User 부분으로 옮겨져야 할 것 같음)
     */
    @GetMapping("/application")
    public List<BoardListResponseDto> findAppliedBoardByUserID(@CookieValue final Long userId) {

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
