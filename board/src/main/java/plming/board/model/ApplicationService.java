package plming.board.model;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.entity.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

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





}
