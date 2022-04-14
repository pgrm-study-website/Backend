package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.SearchRequestDto;
import plming.board.entity.BoardRepository;
import plming.exception.CustomException;
import plming.exception.ErrorCode;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final CustomException e = new CustomException(ErrorCode.BAD_SEARCH);

    public ResponseEntity<Object> search(final SearchRequestDto params, final Pageable pageable) {

        if(isConditionNull(params)) {
            return ResponseEntity.status(200).body(boardService.findAllByDeleteYn(pageable));
        } else {
            if(params.getPeriod() != null) {
                Collections.sort(params.getPeriod());
                if(params.getPeriod().size() == 1){
                    params.setPeriod(List.of(params.getPeriod().get(0), params.getPeriod().get(0)));
                }
            }

            if(params.getParticipantMax() != null) {
                Collections.sort(params.getParticipantMax());
                if(params.getParticipantMax().size() == 1) {
                    params.setParticipantMax(List.of(params.getParticipantMax().get(0), params.getParticipantMax().get(0)));
                }
            }

            return searchAllCondition(params, pageable);
        }
    }

    /**
     * 모든 조건이 null인지 검사
     */
    private boolean isConditionNull(SearchRequestDto params) {
        return params.getSearchType() == null && params.getKeyword() == null
                && params.getCategory() == null && params.getPeriod() == null && params.getStatus() == null
                && params.getTagIds() == null && params.getParticipantMax() == null;
    }

    /**
     * 모든 조건 적용해서 검색
     */
    @Transactional
    public ResponseEntity<Object> searchAllCondition(final SearchRequestDto params, final Pageable pageable) {

        return ResponseEntity.ok(boardService.getBoardListResponseFromPage(boardRepository.searchAllCondition(params, pageable)));
    }
}