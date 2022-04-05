package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.board.dto.BoardListResponseDto;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.board.exception.ErrorResponse;
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
    public List<BoardListResponseDto> findAllByUserId(@CookieValue final Long userId) {
        return boardService.findAllByUserId(userId);
//        return boardService.findAllByUserId(jwtTokenProvider.getUserId(token));
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
    public ResponseEntity<Object> apply(@PathVariable final Long id, @CookieValue final Long userId) {

        //Long userId = jwtTokenProvider.getUserId(token);
        String appliedStatus = boardService.apply(id, userId);
        CustomException e = new CustomException(ErrorCode.NOT_ACCEPTABLE);

        if (appliedStatus.equals("거절")) {
            return ResponseEntity.status(e.getErrorCode().getStatus().value())
                    .body(new ErrorResponse(e.getErrorCode(), "참여 승인이 거절된 게시글입니다."));
        }
        if (appliedStatus.equals("신청")) {
            return ResponseEntity.status(e.getErrorCode().getStatus().value())
                    .body(new ErrorResponse(e.getErrorCode(), "이미 신청한 게시글입니다."));
        }
        if (appliedStatus.equals("마감")) {
            return ResponseEntity.status(e.getErrorCode().getStatus().value())
                    .body(new ErrorResponse(e.getErrorCode(), "신청 마감된 게시글입니다."));
        }

        return ResponseEntity.status(201).body(appliedStatus);
    }


    /**
     * 신청 게시글 리스트 조회 - 사용자 ID 기준 (User 부분으로 옮겨져야 할 것 같음)
     */
    @GetMapping("/application")
    public List<BoardListResponseDto> findAppliedBoardByUserID(@CookieValue final Long userId) {
        return boardService.findAppliedBoardByUserId(userId);
//        return boardService.findAppliedBoardByUserId(jwtTokenProvider.getUserId(token));
    }

    /**
     * 신청 사용자 리스트 조회 - 게시글 ID 기준
     */
    @GetMapping("/{id}/application")
    public List<UserResponseDto> findAppliedUserByBoardId(@PathVariable final Long id) {

        return boardService.findAppliedUserByBoardId(id);
    }

    /**
     * 참여 사용자 리스트 조회 - 게시글 ID 기준
     */
    @GetMapping("/{id}/participant")
    public List<UserResponseDto> findParticipantUserByBoardId(@PathVariable final Long id) {

        return boardService.findParticipantUserByBoardId(id);
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    @PatchMapping("/{id}/application")
    public String updateAppliedStatus(@PathVariable final Long id, @CookieValue final Long userId, @RequestParam final String status) {

        return boardService.updateAppliedStatus(id, userId, status);
//        return boardService.updateAppliedStatus(id, jwtTokenProvider.getUserId(token), status);
    }
}
