package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    /**
     * 제목으로 검색
     */
    public ResponseEntity<Object> searchTitle(final String keyword) {

        boolean isNull = isResultNull(keyword);

        return isNull ? ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(e.getErrorCode())) :
                ResponseEntity.ok(toBoardListDto(boardRepository.searchTitle(keyword)));
    }

    /**
     * 내용으로 검색
     */
    public ResponseEntity<Object> searchContent(final String keyword) {

        boolean isNull = isResultNull(keyword);

        return isNull ? ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(e.getErrorCode())) :
                ResponseEntity.ok(toBoardListDto(boardRepository.searchContent(keyword)));
    }

    /**
     * 카테고리로 검색
     */
    public ResponseEntity<Object> searchCategory(final List<String> keywords) {

        boolean isNull = isResultNull(keywords);

        return isNull ? ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(e.getErrorCode())) :
                ResponseEntity.ok(toBoardListDto(boardRepository.searchCategory(keywords)));
    }

    /**
     * 태그로 검색
     */
    public ResponseEntity<Object> searchTag(final List<Integer> keywords) {

        boolean isNull = isResultNull(keywords);

        return isNull ? ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(e.getErrorCode())) :
                ResponseEntity.ok(toBoardListDto(boardRepository.searchTag(keywords)));
    }

    /**
     * 검색 결과가 null인지 검사
     */
    private boolean isResultNull(final Object keyword) {

        if(keyword == null) {
            return true;
        }
        else {
            return false;
        }
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
