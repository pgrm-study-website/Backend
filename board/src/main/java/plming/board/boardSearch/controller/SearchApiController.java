package plming.board.boardSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import plming.board.boardSearch.dto.SearchRequestDto;
import plming.board.boardSearch.service.SearchService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<Object> search(@Nullable @RequestParam final String searchType,
                                         @Nullable @RequestParam final String keyword, @Nullable @RequestParam final List<String> category,
                                         @Nullable @RequestParam final List<String> status, @Nullable @RequestParam final List<Integer> tagIds,
                                         @Nullable @RequestParam final List<Integer> period, @Nullable @RequestParam final List<Integer> participantMax
            , final Pageable pageable) {

        SearchRequestDto params = SearchRequestDto.builder()
                .searchType(searchType).keyword(keyword).category(category)
                .status(status).tagIds(tagIds).period(period).participantMax(participantMax)
                .build();

        return searchService.search(params, pageable);
    }
}