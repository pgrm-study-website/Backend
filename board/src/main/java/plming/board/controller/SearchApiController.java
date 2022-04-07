package plming.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping()
//    public Map<String, Object> searchTitle(@RequestBody final SearchRequestDto params) {
//
//        Map<String, Object> search = params.toEntity();
//        String type = search.get("type").toString();
//
//        if(type.equals("title")) {
//            return searchService.searchTitle((String) search.get("keyword"));
//        } else if(type.equals("content")) {
//            return searchService.searchContent((String) search.get("keyword"));
//        } else if(type.equals("category")) {
//            return searchService.searchCategory((List<String>) search.get("keyword"));
//        } else if(type.equals("tag")) {
//            return searchService.searchTag((List<Integer>) search.get("keyword"));
//        }
//
//        return null;
//    }


    @GetMapping()
    public ResponseEntity<Object> searchTitle(@RequestBody final SearchRequestDto params) {

        Map<String, Object> search = params.toEntity();
        String type = search.get("type").toString();

        if(type.equals("title")) {
            return searchService.searchTitle((String) search.get("keyword"));
        } else if(type.equals("content")) {
            return searchService.searchContent((String) search.get("keyword"));
        } else if(type.equals("category")) {
            return searchService.searchCategory((List<String>) search.get("keyword"));
        } else if(type.equals("tag")) {
            return searchService.searchTag((List<Integer>) search.get("keyword"));
        }

        return null;
    }
}
