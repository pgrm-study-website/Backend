package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import plming.board.dto.SearchRequestDto;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.board.exception.ErrorResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final CustomException e = new CustomException(ErrorCode.BAD_SEARCH);

    public ResponseEntity<Object> search(final String keyword, final SearchRequestDto params) {

        CustomException e = new CustomException(ErrorCode.POSTS_NOT_FOUND);

        if(keyword == null && params == null) {    // 1. 둘 다 null (검색어가 없는 경우)
            return ResponseEntity.ok(toBoardListDto(boardRepository.findAllByDeleteYn('0', Sort.by(Sort.Direction.DESC, "id", "createDate"))));
        }
        else if(params != null && keyword == null) {   // 2. keyword만 null (단독 검색)

            if(params.getType().equals("title")) {
                return searchTitle((String) params.getKeywords());
            } else if(params.getType().equals("content")) {
                return searchContent((String) params.getKeywords());
            } else if(params.getType().equals("category")) {
                return searchCategory((List<String>) params.getKeywords());
            } else {
                return searchTag((List<Integer>) params.getKeywords());
            }
        }
        else if(keyword != null && params == null) { // 3. params만 null (제목 + 내용 검색)
            if(keyword.isEmpty()) {
                return ResponseEntity.ok(toBoardListDto(boardRepository.findAllByDeleteYn('0', Sort.by(Sort.Direction.DESC, "id", "createDate"))));
            }
            return searchTitleAndContent(keyword);
        }
        else {

            if(params.getType().equals("title&category")) {
                return searchTitleAndCategory(keyword, (List<String>) params.getKeywords());
            } else if(params.getType().equals("title&tag")) {
                return searchTitleAndTag(keyword, (List<Integer>) params.getKeywords());
            } else if(params.getType().equals("content&category")) {
                return searchContentAndCategory(keyword, (List<String>) params.getKeywords());
            } else if(params.getType().equals("content&tag")) {
                return searchContentAndTag(keyword, (List<Integer>) params.getKeywords());
            }
        }

        return ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(ErrorCode.POSTS_NOT_FOUND));
    }

    /**
     * 제목으로 검색
     */
    private ResponseEntity<Object> searchTitle(final String keyword) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchTitle(keyword)));
    }

    /**
     * 내용으로 검색
     */
    private ResponseEntity<Object> searchContent(final String keyword) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchContent(keyword)));
    }

    /**
     * 카테고리로 검색
     */
    private ResponseEntity<Object> searchCategory(final List<String> keywords) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchCategory(keywords)));
    }

    /**
     * 태그로 검색
     */
    private ResponseEntity<Object> searchTag(final List<Integer> keywords) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchTag(keywords)));
    }

    /**
     * 제목 + 내용으로 검색
     */
    private ResponseEntity<Object> searchTitleAndContent(final String keyword) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchTitleAndContent(keyword)));
    }

    /**
     * 제목 + 카테고리로 검색
     */
    private ResponseEntity<Object> searchTitleAndCategory(final String keyword, final List<String> categories) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchTitleAndCategory(keyword, categories)));
    }

    /**
     * 제목 + 태그로 검색
     */
    private ResponseEntity<Object> searchTitleAndTag(final String keyword, final List<Integer> tags) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchTitleAndTag(keyword, tags)));
    }

    /**
     * 내용 + 카테고리로 검색
     */
    private ResponseEntity<Object> searchContentAndCategory(final String keyword, final List<String> categories) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchContentAndCategory(keyword, categories)));
    }

    /**
     * 내용 + 태그로 검색
     */
    private ResponseEntity<Object> searchContentAndTag(final String keyword, final List<Integer> tags) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchContentAndTag(keyword, tags)));
    }

    /**
     * BoardListDto 로 변환
     */
    private Map<String, Object> toBoardListDto(final List<Board> list) {

        Map<String, Object> result = new HashMap<String, Object>(2);
        result.put("postCount", list.size());
        result.put("posts", boardService.getBoardListResponse(list));

        return result;
    }
}