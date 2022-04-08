package plming.board.repository;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.SearchRequestDto;
import plming.board.entity.Board;
import plming.board.entity.BoardTag;

import java.util.List;

public interface BoardCustomRepository {

    /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
     */
    List<Board> findAllByDeleteYn(final char deleteYn, final Sort sort);

    /**
     * 게시글 리스트 조회 - (사용자 Id 기준)
     */
    List<Board> findAllByUserId(final Long userId, final Sort sort);

    /**
     * 게시글 ID 기준 태그 id 리스트 조회
     */
    List<BoardTag> findAllByBoardId(final Long boardId);

    /**
     * 게시글 ID 기준 태그 id 삭제
     */
    @Transactional
    void deleteAllByBoardId(final Long boardId);

    /**
     * 게시글 검색 - 모든 조건
     */
    List<Board> searchAllCondition(final SearchRequestDto params);

}
