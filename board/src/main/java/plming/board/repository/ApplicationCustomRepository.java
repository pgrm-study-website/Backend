package plming.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

public interface ApplicationCustomRepository {

    /**
     * 신청 게시글 리스트 조회 - (사용자 Id 기준)
     */
    Page<Board> findAppliedBoardByUserId(final Long userId, final Pageable pageable);

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
    Application updateAppliedStatus(final Long boardId, final String nickname, final String status);

    /**
     * 게시글 신청 취소
     */
    @Transactional
    void cancelApplied(final Long boardId, final Long userId);
}