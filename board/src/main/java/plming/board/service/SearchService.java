package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Object> search(final String type, final SearchRequestDto params) {

        CustomException e = new CustomException(ErrorCode.POSTS_NOT_FOUND);

        if(type.equals("all")) {
            return searchAllCondition(params);
        }

        return ResponseEntity.status(e.getErrorCode().getStatus().value()).body(new ErrorResponse(ErrorCode.POSTS_NOT_FOUND));
    }

    /**
     * 모든 조건 적용해서 검색
     */
    private ResponseEntity<Object> searchAllCondition(final SearchRequestDto params) {

        return ResponseEntity.ok(toBoardListDto(boardRepository.searchAllCondition(params)));
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