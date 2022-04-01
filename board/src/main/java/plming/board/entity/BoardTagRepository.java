package plming.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
    /**
     * 게시글 ID 기준 태그 id 리스트 조회
     */
    List<BoardTag> findAllByBoardId(final Long boardId);

    /**
     * 게시글 ID 기준 태그 id 삭제
     */
    void deleteAllByBoardId(final Long boardId);

}
