package plming.board.boardComment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.board.boardComment.dto.UpdateCommentRequestDto;
import plming.board.boardComment.dto.CommentRequestDto;
import plming.board.boardComment.dto.CommentResponseDto;
import plming.board.boardComment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<Long> registerComment(@PathVariable final Long id, @CookieValue final String token ,@RequestBody final CommentRequestDto comment) {

        return ResponseEntity.status(201).body(commentService.registerComment(id, jwtTokenProvider.getUserId(token), comment));
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentByBoardId(@PathVariable final Long id) {

        return ResponseEntity.ok(commentService.findCommentByBoardId(id));
    }

    @PatchMapping("/comments")
    public ResponseEntity<Long> updateCommentByCommentId(@CookieValue final String token, @RequestBody final UpdateCommentRequestDto params) {

        return ResponseEntity.ok(commentService.updateCommentByCommentId(params.getCommentId(), jwtTokenProvider.getUserId(token), params.getContent()));
    }

    @DeleteMapping("/comments")
    public ResponseEntity deleteCommentByCommentId(@CookieValue final String token, @RequestParam final Long commentId) {

        commentService.deleteCommentByCommentId(commentId, jwtTokenProvider.getUserId(token));
        return ResponseEntity.ok().build();
    }
}