package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Board> findAppliedBoardByUserId(final Long userId) {

        List<Application> applications = applicationRepository.findAppliedBoardByUserId(userId);
        List<Board> appliedBoards = applications.stream().map(Application::getBoard).collect(Collectors.toList());

        return appliedBoards;
    }

    /**
     * 게시글 신청자 리스트 조회
     */
    public List<User> findAppliedUserByBoardId(final Long boardId) {

        List<Application> applications = applicationRepository.findAppliedUserByBoardId(boardId);
        List<User> appliedUsers = applications.stream().map(Application::getUser).collect(Collectors.toList());

        return appliedUsers;
    }

    /**
     * 게시글 참여자 리스트 조회
     */
    public List<User> findParticipantUserByBoardId(final Long boardId) {

        List<Application> applicationList = applicationRepository.findParticipantByBoardId(boardId);
        List<User> participatedUser = applicationList.stream().map(Application::getUser).collect(Collectors.toList());

        return participatedUser;
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
    private List<Application> findApplication(Long boardId, Long userId) {
        return applicationRepository.findAppliedUserByBoardId(boardId).stream().filter(app -> app.getUser().getId().equals(userId)).collect(Collectors.toList());
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
