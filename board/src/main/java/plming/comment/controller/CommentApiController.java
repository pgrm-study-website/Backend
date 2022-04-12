package plming.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.comment.dto.CommentOneResponseDto;
import plming.comment.dto.CommentRequestDto;
import plming.comment.dto.CommentResponseDto;
import plming.comment.dto.UpdateCommentRequestDto;
import plming.comment.entity.Comment;
import plming.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentApiController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("{id}/comments")
    public ResponseEntity<Long> registerComment(@PathVariable final Long id, @CookieValue final String token ,@RequestBody final CommentRequestDto comment) {

        return ResponseEntity.status(201).body(commentService.registerComment(id, jwtTokenProvider.getUserId(token), comment));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDto>> findCommentByBoardId(@PathVariable final Long id) {

        return ResponseEntity.ok(commentService.findCommentByBoardId(id));
    }

    @GetMapping("/comments/users")
    public ResponseEntity<List<CommentOneResponseDto>> findCommentByUserId(@CookieValue final String token) {

        return ResponseEntity.ok(commentService.findCommentByUserId(jwtTokenProvider.getUserId(token)));
    }

    @PatchMapping("/comments")
    public ResponseEntity<Long> updateCommentByCommentId(@CookieValue final String token, @RequestBody final UpdateCommentRequestDto params) {

        return ResponseEntity.ok(commentService.updateCommentByCommentId(params.getCommentId(), jwtTokenProvider.getUserId(token), params.getContent()));
    }

    @DeleteMapping("/comments")
    public ResponseEntity<Long> deleteCommentByCommentId(@CookieValue final String token, @RequestParam final Long commentId) {

        return ResponseEntity.ok(commentService.deleteCommentByCommentId(commentId, jwtTokenProvider.getUserId(token)));
    }
}
