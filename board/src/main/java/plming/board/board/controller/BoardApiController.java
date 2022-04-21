package plming.board.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.board.board.dto.request.BoardRequestDto;
import plming.board.board.dto.response.BoardResponseDto;
import plming.board.board.dto.response.UserBoardListResponseDto;
import plming.board.boardApply.dto.ApplicationStatusRequestDto;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.exception.ErrorResponse;
import plming.board.board.service.BoardService;

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
    public ResponseEntity<Long> save(@RequestBody final BoardRequestDto post, @CookieValue String token) {

        Long userId = jwtTokenProvider.getUserId(token);
        return ResponseEntity.status(201).body(boardService.save(post, userId));
    }

    /**
     * 게시글 수정
     */
    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable final Long id, @RequestBody final BoardRequestDto post, @CookieValue final String token) {

        CustomException e1 = new CustomException(ErrorCode.NOT_ACCEPTABLE);
        Long userId = jwtTokenProvider.getUserId(token);

        if (boardService.update(id, userId, post).equals("인원 수")) {
            return ResponseEntity.status(e1.getErrorCode().getStatus().value())
                    .body(new ErrorResponse(e1.getErrorCode(), "현재 게시글에 참여한 인원 수보다 참여 가능한 인원 수를 더 작게 수정할 수 없습니다."));
        }
        if (boardService.update(id, userId, post).equals("모집 완료")) {
            return ResponseEntity.status(e1.getErrorCode().getStatus().value())
                    .body(new ErrorResponse(e1.getErrorCode(), "현재 게시글은 모집 완료된 게시글로 수정이 불가능합니다."));
        }

        return ResponseEntity.status(200).body(boardService.update(id, userId, post));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable final Long id, @CookieValue final String token) {

        boardService.delete(id, jwtTokenProvider.getUserId(token));

        return ResponseEntity.ok().build();
    }

    /**
     * 게시글 리스트 조회 - 사용자 ID 기준
     */
    @GetMapping("/user")
    public UserBoardListResponseDto findAllByUserId(@CookieValue final String token) {

        return boardService.findAllByUserId(jwtTokenProvider.getUserId(token));
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
    public ResponseEntity<Object> apply(@PathVariable final Long id, @CookieValue final String token) {

        Long userId = jwtTokenProvider.getUserId(token);
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
     * 게시글 신청자 리스트 + 참여자 리스트 통합
     */

    @GetMapping("/{id}/application/users")
    public ResponseEntity<Object> findAppliedUserByBoardId(@PathVariable final Long id, @CookieValue final String token) {

        return ResponseEntity.status(200).body(boardService.findAppliedUsers(id, jwtTokenProvider.getUserId(token)));
    }

    /**
     * 특정 게시글 신청 상태 조회
     */
    @GetMapping("/{id}/application")
    @ResponseStatus(HttpStatus.OK)
    public String findApplicationStatus(@PathVariable final Long id, @CookieValue String token) {

        return boardService.findApplicationStatus(id, jwtTokenProvider.getUserId(token));
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    @PatchMapping("/{id}/application")
    @ResponseStatus(HttpStatus.OK)
    public void updateAppliedStatus(@PathVariable final Long id, @CookieValue final String token,
                                      @RequestParam final String status, @RequestParam final String nickname) {

        ApplicationStatusRequestDto body = ApplicationStatusRequestDto.builder().status(status).nickname(nickname).build();
        boardService.updateAppliedStatus(id, jwtTokenProvider.getUserId(token), body.getNickname(), body.getStatus());
    }

    /**
     * 게시글 신청 취소
     */
    @DeleteMapping("/{id}/application")
    public void canceledApply(@PathVariable final Long id, @CookieValue final String token) {
        boardService.cancelApplied(id, jwtTokenProvider.getUserId(token));
    }
}