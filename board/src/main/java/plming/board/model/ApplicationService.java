package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
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
    public Long save(final Long boardId, final Long userId) {
        Application application = Application.builder()
                .board(boardRepository.getById(boardId))
                .user(userRepository.getById(userId))
                .status("대기")
                .build();

        applicationRepository.save(application);

        return boardId;
    }

    /**
     * 신청한 게시글 리스트 조회
     */
    public List<Board> findByAppliedBoardByUserId(final Long userId) {

        List<Application> applications = applicationRepository.findAllByUserId(userId);
        List<Board> appliedBoards = applications.stream().map(Application::getBoard).collect(Collectors.toList());

        return appliedBoards;
    }

    /**
     * 게시글 신청자 리스트 조회
     */
    public List<User> findAppliedUserByBoardId(final Long boardId) {

        List<Application> applications = applicationRepository.findAllByBoardId(boardId);
        List<User> appliedUsers = applications.stream().map(Application::getUser).collect(Collectors.toList());

        return appliedUsers;
    }

    /**
     * 게시글 신청 상태 업데이트
     */
    @Transactional
    public Application updateStatus(final Long boardId, final Long userId, final String status) {

        List<Application> boardApplications = applicationRepository.findAllByBoardId(boardId);
        List<Application> applicationList = boardApplications.stream().filter(app -> app.getUser().getId().equals(userId)).collect(Collectors.toList());
        applicationList.get(0).update(status);

        return applicationList.get(0);
    }

}
