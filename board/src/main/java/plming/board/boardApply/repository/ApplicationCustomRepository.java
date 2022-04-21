package plming.board.boardApply.repository;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import plming.board.boardApply.entity.Application;
import plming.board.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

public interface ApplicationCustomRepository {

    /**
     * 신청 게시글 리스트 조회 - 페이징 X
     */
    List<Board> findAppliedBoard(final Long userId);

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    List<Application> findAppliedUserByBoardId(final Long boardId);

    /**
     * 참여 사용자 리스트 조회 - (게시글 ID 기준)
     */
    List<User> findParticipantByBoardId(final Long boardId);


    /**
     * 게시글 신청 조회
     */
    Application findApplication(final Long boardId, final Long userId);

    /**
     * 게시글 신청 상태 업데이트
     */
    @Transactional
    void updateAppliedStatus(final Long boardId, final String nickname, final String status);

    /**
     * 게시글 모집 상태 업데이트
     */
    @Transactional
    void updateBoardStatus(final Long boardId, final String status);

    /**
     * 게시글 신청 취소
     */
    @Transactional
    void cancelApplied(final Long boardId, final Long userId);
}