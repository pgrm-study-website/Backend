package plming.board.controller;

import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.board.dto.ApplicationStatusRequestDto;
import plming.board.dto.BoardListResponseDto;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.board.exception.ErrorResponse;
import plming.board.service.BoardService;
import plming.user.dto.UserListResponseDto;

import java.util.List;
import java.util.Map;

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

//    /**
//     * 게시글 리스트 조회
//     */
//    @GetMapping
//    public Page<BoardListResponseDto> findAll(Pageable pageable) {
//
//        return boardService.findAllByDeleteYn(pageable);
//    }

    /**
     * 게시글 리스트 조회 - 사용자 ID 기준
     */
    @GetMapping("/user")
    public Page<BoardListResponseDto> findAllByUserId(@CookieValue final String token, final Pageable pageable) {

        return boardService.findAllByUserId(jwtTokenProvider.getUserId(token), pageable);
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
     * 신청 게시글 리스트 조회 - 사용자 ID 기준 (User 부분으로 옮겨져야 할 것 같음)
     */
    @GetMapping("/application")
    public Page<BoardListResponseDto> findAppliedBoardByUserID(@CookieValue final String token, Pageable pageable) {
        return boardService.findAppliedBoardByUserId(jwtTokenProvider.getUserId(token), pageable);
    }

    @GetMapping("/{id}/application/users")
    public ResponseEntity<Object> findAppliedUserByBoardId(@PathVariable final Long id, @CookieValue final String token) {

        return ResponseEntity.status(200).body(boardService.findAppliedUsers(id, jwtTokenProvider.getUserId(token)));
    }

    /**
     * 신청 사용자 리스트 조회 - 게시글 ID 기준
     */
    @GetMapping("/{id}/application")
    public List<Map<String, Object>> findAppliedUserByBoardId(@PathVariable final Long id) {

        return boardService.findAppliedUserByBoardId(id);
    }

    /**
     * 참여 사용자 리스트 조회 - 게시글 ID 기준
     */
    @GetMapping("/{id}/participant")
    public List<UserListResponseDto> findParticipantUserByBoardId(@PathVariable final Long id) {

        return boardService.findParticipantUserByBoardId(id);
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    @PatchMapping("/{id}/application")
    public String updateAppliedStatus(@PathVariable final Long id, @CookieValue final String token, @RequestBody final ApplicationStatusRequestDto body) {

        return boardService.updateAppliedStatus(id, jwtTokenProvider.getUserId(token), body.getNickname(), body.getStatus());
    }
}
