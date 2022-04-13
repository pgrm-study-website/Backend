package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.exception.exception.CustomException;
import plming.exception.exception.ErrorCode;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public String save(final Long boardId, final Long userId) {

        if (findApplication(boardId, userId).size() == 0 && isMaxNum(boardId) && isStatusTrue(boardId, userId)) {
            Application application = Application.builder()
                    .board(boardRepository.getById(boardId))
                    .user(userRepository.getById(userId))
                    .status("대기")
                    .build();
            applicationRepository.save(application);

            return applicationRepository.getById(application.getId()).getBoard().getId().toString();
        }

        /**
         * 신청 인원이 가득 찼을 경우 자동으로 모집 완료 상태로 변경
         */
        if(!isMaxNum(boardId)) {
            Board board = boardRepository.getById(boardId);
            board.updateStatus("모집 완료");
        }

        if ((findApplication(boardId, userId).size() == 0 && !isMaxNum(boardId))) {
            return "마감";
        } else if (!isStatusTrue(boardId, userId)) {
            return "거절";
        } else {
            return "신청";
        }
    }

    /**
     * 게시글 참여 인원이 최대 인원인지 확인
     * 최대 인원이 아닌 경우 true 반환
     */
    public boolean isMaxNum(final Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        Integer participantMax = board.getParticipantMax();
        Integer participantNum = countParticipantNum(boardId);

        return (participantNum == 0) || (participantMax > participantNum) ? true : false;
    }

    /**
     * 신청 상태가 거절인지 확인
     * 신청 상태가 거절이 아닌 경우 true 반환
     */
    public boolean isStatusTrue(final Long boardId, final Long userId) {
        if (findApplication(boardId, userId).size() == 0) {
            return true;
        }

        return !findApplication(boardId, userId).get(0).getStatus().equals("거절") ? true : false;
    }

    /**
     * 신청한 게시글 리스트 조회
     */
    public Page<Board> findAppliedBoardByUserId(final Long userId, final Pageable pageable) {

        return applicationRepository.findAppliedBoardByUserId(userId, pageable);
    }

    /**
     * 게시글 신청자 리스트 조회
     */
    public List<User> findAppliedUserByBoardId(final Long boardId) {

        return applicationRepository.findAppliedUserByBoardId(boardId);
    }

    /**
     * 게시글 참여자 리스트 조회
     */
    public List<User> findParticipantUserByBoardId(final Long boardId) {

        return applicationRepository.findParticipantByBoardId(boardId);
    }

    /**
     * 게시글 참여자 수 계산
     */
    public Integer countParticipantNum(final Long boardId) {

        return findParticipantUserByBoardId(boardId).size();
    }

    /**
     * 게시글 신청 조회
     */
    private List<Application> findApplication(final Long boardId, final Long userId) {

        return applicationRepository.findApplication(boardId, userId);
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    public Application updateAppliedStatus(final Long boardId, final Long userId, final String status) {

        return applicationRepository.updateAppliedStatus(boardId, userId, status).get(0);
    }

    /**
     * 게시글 신청 취소
     */
    @Transactional
    public void cancelApplied(final Long boardId, final Long userId) {

        applicationRepository.cancelApplied(boardId, userId);
    }
}