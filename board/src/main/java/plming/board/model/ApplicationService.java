package plming.board.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
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

    public List<Board> findByAppliedBoardByUserId(final Long userId) {

        List<Application> applications = applicationRepository.findAllByUserId(userId);
        List<Board> appliedBoards = applications.stream().map(Application::getBoard).collect(Collectors.toList());

        return appliedBoards;
    }

}
