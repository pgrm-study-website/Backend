package plming.board.controller;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import plming.board.dto.SearchRequestDto;
import plming.board.service.SearchService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Object> search(@Nullable @RequestParam final String keyword, @Nullable @RequestBody final SearchRequestDto params) {

        return searchService.search(keyword, params);
    }
}
