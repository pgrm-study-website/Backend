package plming.board.boardApply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.boardApply.entity.Application;
import plming.board.boardApply.repository.ApplicationRepository;
import plming.board.board.entity.Board;
import plming.board.board.repository.BoardRepository;
import plming.event.ApplicationCreateEvent;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String save(final Long boardId, final Long userId) {

        if (findApplication(boardId, userId) == null && isMaxNum(boardId) && isStatusTrue(boardId, userId)) {
            Application application = Application.builder()
                    .board(boardRepository.getById(boardId))
                    .user(userRepository.getById(userId))
                    .status("대기")
                    .build();
            Application savedApplication = applicationRepository.save(application);
            eventPublisher.publishEvent(new ApplicationCreateEvent(savedApplication, application.getBoard().getUser(), NotificationType.apply));

            return applicationRepository.getById(application.getId()).getBoard().getId().toString();
        }

        if ((findApplication(boardId, userId) == null && !isMaxNum(boardId))) {
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

        return (participantNum == 0) || (participantMax > participantNum);
    }

    /**
     * 신청 상태가 거절인지 확인
     * 신청 상태가 거절이 아닌 경우 true 반환
     */
    public boolean isStatusTrue(final Long boardId, final Long userId) {
        if (findApplication(boardId, userId) == null) {
            return true;
        }

        return !findApplication(boardId, userId).getStatus().equals("거절");
    }

    /**
     * 신청한 게시글 리스트 조회
     */
    public List<Board> findAppliedBoard(final Long userId) {

        return applicationRepository.findAppliedBoard(userId);
    }

    /**
     * 게시글 신청자 리스트 조회
     */
    public List<Application> findAppliedUserByBoardId(final Long boardId) {

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
    public Application findApplication(final Long boardId, final Long userId) {

        return applicationRepository.findApplication(boardId, userId);
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    public void updateAppliedStatus(final Long boardId, final String nickname, final String status) {

        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Application application = applicationRepository.findApplication(boardId, user.getId());

        if(application == null) {
            throw new CustomException(ErrorCode.ALREADY_CANCELED);
        }

        applicationRepository.updateAppliedStatus(boardId, nickname, status);

        // 신청 인원이 가득 찼을 경우 자동으로 모집 완료 상태로 변경
        if(!isMaxNum(boardId)) {
            applicationRepository.updateBoardStatus(boardId, "모집 완료");
        }

        if(status.equals("승인")) {
            eventPublisher.publishEvent(new ApplicationCreateEvent(application, application.getUser(), NotificationType.accept));
        } else if(status.equals("거절")){
            eventPublisher.publishEvent(new ApplicationCreateEvent(application, application.getUser(), NotificationType.reject));
        }
    }

    /**
     * 게시글 신청 취소
     */
    @Transactional
    public void cancelApplied(final Long boardId, final Long userId) {

        Application application = applicationRepository.findApplication(boardId, userId);
        if (application == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        } else {
            applicationRepository.cancelApplied(boardId, userId);
        }
    }
}