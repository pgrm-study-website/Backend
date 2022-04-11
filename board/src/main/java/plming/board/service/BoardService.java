package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.BoardListResponseDto;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.*;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.board.entity.BoardRepository;
import plming.board.entity.BoardTagRepository;
import plming.user.dto.UserListResponseDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.service.UserService;

import java.util.*;
import java.util.stream.Collectors;


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
    public Long save(final BoardRequestDto params, final Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Board entity = boardRepository.save(params.toEntity(user));
        List<Long> boardTagIds = params.getTagIds();
        boardTagService.save(boardTagIds, entity);

        return entity.getId();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public String update(final Long id, final Long userId ,final BoardRequestDto params) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if(entity.getUser().getId().equals(userId)) {
            if (entity.getStatus().equals("모집 완료")) {
                return "모집 완료";
            }
            if ((!(params.getParticipantMax() == null)) && (params.getParticipantMax() < countParticipantNum(id))) {
                return "인원 수";
            }

            entity.update(params.getParticipantMax(), params.getTitle(), params.getContent(), params.getCategory(), params.getStatus(), params.getPeriod());
            boardTagRepository.deleteAllByBoardId(id);
            boardTagService.save(params.getTagIds(), entity);

            return entity.getId().toString();
        } else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(final Long id, final Long userId) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if(entity.getUser().getId().equals(userId)) {
            if(entity.getDeleteYn() == '1') {
                throw new CustomException(ErrorCode.ALREADY_DELETE);
            }
            entity.delete();
            boardTagRepository.deleteAllByBoardId(id);
        }
        else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    public Page<BoardListResponseDto> findAllByDeleteYn(final Pageable pageable) {

        Page<Board> list = boardRepository.findAllPageSort(pageable);
        return getBoardListResponseFromPage(list);
    }

    /**
     * 게시글 리스트 조회 - (사용자 ID 기준)
     */
    public Page<BoardListResponseDto> findAllByUserId(final Long userId, final Pageable pageable) {

        Page<Board> list = boardRepository.findAllByUserId(userId, pageable);
        return getBoardListResponseFromPage(list);
    }

    /**
     * 각 게시글의 태그 이름 조회 후 BoardListResponseDto 반환
     */
    public List<BoardListResponseDto> getBoardListResponse(List<Board> list) {

        List<BoardListResponseDto> result = new ArrayList<BoardListResponseDto>();
        for (Board post : list) {
            List<String> tagName = boardTagService.findTagNameByBoardId(post.getId());
            Integer participantNum = applicationService.countParticipantNum(post.getId());
            result.add(new BoardListResponseDto(post, participantNum, tagName));
        }
        return result;
    }

    /**
     * 게시글 상세 정보 조회
     */
    @Transactional
    public BoardResponseDto findById(final Long id) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (entity.getDeleteYn() == '1') {
            throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
        }

        entity.increaseCount();
        List<String> boardTagName = boardTagService.findTagNameByBoardId(id);

        return new BoardResponseDto(entity, applicationService.countParticipantNum(id), boardTagName);
    }

    /**
     * 각 게시글의 태그 이름 조회 후 BoardListResponseDto 반환
     */
    public Page<BoardListResponseDto> getBoardListResponseFromPage(Page<Board> list) {

        List<BoardListResponseDto> result = new ArrayList<BoardListResponseDto>();
        List<Board> boards = list.getContent();
        for (Board post : boards) {
            Integer participantNum = applicationService.countParticipantNum(post.getId());
            result.add(new BoardListResponseDto(post, participantNum));
        }

        return new PageImpl<>(result);
    }

    /**
     * 각 게시글의 태그 이름 조회 후 BoardListResponseDto 반환
     */
    public List<BoardListResponseDto> getBoardListResponseFromPageTest(List<Board> list) {

        List<BoardListResponseDto> result = new ArrayList<BoardListResponseDto>();
        // List<Board> boards = list.getContent();
        for (Board post : list) {
            Integer participantNum = applicationService.countParticipantNum(post.getId());
            result.add(new BoardListResponseDto(post, participantNum));
        }

        return result;
    }

    /**
     * 게시글 신청 하기
     */
    @Transactional
    public String apply(final Long boardId, final Long userId) {

        return applicationService.save(boardId, userId);
    }

    /**
     * 신청 게시글 리스트 조회 - (사용자 ID 기준)
     */
    @Transactional
    public Page<BoardListResponseDto> findAppliedBoardByUserId(final Long userId, final Pageable pageable) {

        Page<Board> appliedBoards = applicationService.findAppliedBoardByUserId(userId, pageable);

        return getBoardListResponseFromPage(appliedBoards);
    }

    /**
     * 신청 사용자 리스트 조회 + 참여자 리스트 조회
     */
    public Object findAppliedUsers(final Long boardId, final Long userId) {

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if(board.getUser().getId().equals(userId)) {
            return findAppliedUserByBoardId(boardId);
        } else {
            return findParticipantUserByBoardId(boardId);
        }
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    public List<Map<String, Object>> findAppliedUserByBoardId(final Long boardId) {

        List<Application> applicationList = applicationService.findAppliedUserByBoardId(boardId);

        List<Map<String, Object>> result = new ArrayList<>();

        applicationList.forEach(application -> {
            Map<String, Object> map = new HashMap<>(2);
            map.put("user", userService.getUserList(application.getUser().getId()));
            map.put("status", application.getStatus());
            result.add(map);
        });

        return result;
    }

    /**
     * 참여 사용자 리스트 조회 - (게시글 ID 기준)
     */
    public List<UserListResponseDto> findParticipantUserByBoardId(final Long boardId) {

        List<User> participatedUsers = applicationService.findParticipantUserByBoardId(boardId);

        return participatedUsers.stream().map(User::getId).map(userService::getUserList).collect(Collectors.toList());
    }

    /**
     * 게시글 신청 정보 업데이트
     */
    @Transactional
    public String updateAppliedStatus(final Long boardId, final Long userId, final String nickname, final String status) {

        if(boardRepository.getById(boardId).getUser().getId().equals(userId)) {
            Application application = applicationService.updateAppliedStatus(boardId, nickname, status);

            return application.getStatus();
        }
        else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    /**
     * 게시글 참여자 수 계산
     */
    public Integer countParticipantNum(final Long boardId) {

        return applicationService.countParticipantNum(boardId);
    }

    /**
     * 지원 취소하기 - Application에서 데이터 삭제
     */
    public void cancelApplied(final Long boardId, final Long userId) {

        applicationService.cancelApplied(boardId, userId);
    }
}

