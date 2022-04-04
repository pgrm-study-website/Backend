package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.*;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.dto.UserResponseDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardTagRepository boardTagRepository;
    private final BoardTagService boardTagService;
    private final UserService userService;
    private final ApplicationService applicationService;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final BoardRequestDto params) {

        User user = userRepository.getById(params.getUserId());
        Board entity = boardRepository.save(params.toEntity(user));
        List<Long> boardTagIds = params.getTagIds();
        boardTagService.save(boardTagIds, entity);

        return entity.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(final Long id, final BoardRequestDto params) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.getTitle(), params.getContent(), params.getCategory(), params.getStatus(), params.getPeriod());

        boardTagRepository.deleteAllByBoardId(id);
        boardTagService.save(params.getTagIds(), entity);

        return id;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(final Long id) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.delete();
        boardTagRepository.deleteAllByBoardId(id);
        return id;
    }

    /**
     * 게시글 리스트 조회
     */
    public List<BoardResponseDto> findAll() {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAll(sort);
        return getTagName(list);
    }

    /**
     * 게시글 리스트 조회 - (사용자 ID 기준)
     */
    public List<BoardResponseDto> findAllByUserId(final Long userId) {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAllByUserId(userId, sort);
        return getTagName(list);
    }

    /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
     */
    public List<BoardResponseDto> findAllByDeleteYn(final char deleteYn) {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAllByDeleteYn(deleteYn, sort);
        return getTagName(list);
    }

    /**
     * 각 게시글의 태그 이름 조회
     */
    public List<BoardResponseDto> getTagName(List<Board> list) {
        List<BoardResponseDto> result = new ArrayList<BoardResponseDto>();
//        List<List<String>> tags= list.stream().map(Board::getId).map(boardTagService::findTagNameByBoardId).collect(Collectors.toList());
        for (Board post : list) {
                List<String> tagName = boardTagService.findTagNameByBoardId(post.getId());
                result.add(new BoardResponseDto(post, tagName));
        }
        return result;
    }

    /**
     * 게시글 상세 정보 조회
     */
    @Transactional
    public BoardResponseDto findById(final Long id) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.increaseCount();
        List<String> boardTagName = boardTagService.findTagNameByBoardId(id);
        return new BoardResponseDto(entity, boardTagName);
    }

    /**
     * 게시글 신청 하기
     */
    @Transactional
    public Long apply(final Long boardId, final Long userId) {

        return applicationService.save(boardId, userId);
    }

    /**
     * 신청 게시글 리스트 조회 - (사용자 ID 기준)
     */
    public List<BoardResponseDto> findAppliedBoardByUserId(final Long userId) {

        List<Board> appliedBoards = applicationService.findByAppliedBoardByUserId(userId);

        return getTagName(appliedBoards);
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    public List<UserResponseDto> findAppliedUserByBoardId(final Long boardId) {

        List<User> appliedUsers = applicationService.findAppliedUserByBoardId(boardId);

        return appliedUsers.stream().map(User::getId).map(userService::getUser).collect(Collectors.toList());
    }

    /**
     * 게시글 신청 정보 업데이트
     */
    public String updateAppliedStatus(final Long boardId, final Long userId, final String status) {

        Application application = applicationService.updateStatus(boardId, userId, status);

        return application.getStatus();
    }
}
