package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plming.board.dto.SearchRequestDto;
import plming.board.service.SearchService;

import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

    @GetMapping()
    public Map<String, Object> searchTitle(@RequestBody final SearchRequestDto params) {

        Map<String, Object> search = params.toEntity();

        if(search.get("type").equals("제목")) {
            return searchService.searchTitle((String) search.get("keyword"));
        } else if(search.get("type").equals("내용")) {
            return searchService.searchContent((String) search.get("keyword"));
        }

        return null;
    }
}
