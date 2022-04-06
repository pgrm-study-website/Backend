package plming.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import plming.comment.dto.CommentRequestDto;
import plming.comment.service.CommentServiceImpl;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentServiceImpl commentService;

    @PostMapping("/comments")
    public ResponseEntity<Long> registerComment(@RequestBody CommentRequestDto comment) {

        return ResponseEntity.status(201).body(commentService.registerComment(comment));
    }
}
