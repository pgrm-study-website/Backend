package plming.board.repository;

import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;

import java.util.List;

public interface ApplicationCustomRepository {

    /**
     * 신청 게시글 리스트 조회 - (사용자 Id 기준)
     */
    List<Application> findAppliedBoardByUserId(final Long userId);

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    List<Application> findAppliedUserByBoardId(final Long boardId);

    /**
     * 참여 사용자 리스트 조회 - (게시글 ID 기준)
     */
    List<Application> findParticipantByBoardId(final Long boardId);

    /**
     * 게시글 신청 상태 업데이트
     */
    @Transactional
    List<Application> updateAppliedStatus(final Long boardId, final Long userId, final String status);

    /**
     * 게시글 신청 취소
     */
    @Transactional
    void cancelApplied(final Long boardId, final Long userId);
}
