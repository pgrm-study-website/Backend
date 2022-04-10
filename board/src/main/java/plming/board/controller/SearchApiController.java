package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import plming.board.dto.SearchRequestDto;
import plming.board.service.SearchService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Object> search(@Nullable @RequestBody final SearchRequestDto params, final Pageable pageable) {

        return searchService.search(params, pageable);
    }
}
