package plming.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    /**
     * 신청 게시글 리스트 조회 - (사용자 Id 기준)
     */
    List<Application> findAllByUserId(final Long userId);

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    List<Application> findAllByBoardId(final Long boardId);

}