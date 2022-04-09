package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plming.board.dto.SearchRequestDto;
import plming.board.entity.BoardRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final CustomException e = new CustomException(ErrorCode.BAD_SEARCH);

    public ResponseEntity<Object> search(final SearchRequestDto params, final Pageable pageable) {

        CustomException e = new CustomException(ErrorCode.POSTS_NOT_FOUND);

        if(params == null) {
            return ResponseEntity.status(200).body(boardService.findAllByDeleteYn(pageable));
        } else {
            return searchAllCondition(params, pageable);
        }
    }

    /**
     * 모든 조건 적용해서 검색
     */
    private ResponseEntity<Object> searchAllCondition(final SearchRequestDto params, final Pageable pageable) {

        return ResponseEntity.ok(boardService.getBoardListResponseFromPage(boardRepository.searchAllCondition(params, pageable)));
    }
}