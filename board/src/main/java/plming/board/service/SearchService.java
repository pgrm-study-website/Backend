package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plming.board.dto.BoardListResponseDto;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    /**
     * 제목으로 검색
     */
    public Map<String, Object> searchTitle(final String keyword) {

        return toBoardListDto(boardRepository.searchTitle(keyword));
    }

    /**
     * 내용으로 검색
     */
    public Map<String, Object> searchContent(final String keyword) {

        return toBoardListDto(boardRepository.searchContent(keyword));
    }

    /**
     * 카테고리로 검색
     */
    public Map<String, Object> searchCategory(final List<String> keywords) {

        return toBoardListDto(boardRepository.searchCategory(keywords));
    }

    /**
     * 태그로 검색
     */
    public Map<String, Object> searchTag(final List<Integer> keywords) {

        return toBoardListDto(boardRepository.searchTag(keywords));
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
