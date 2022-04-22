package plming.board.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.board.entity.Board;
import plming.board.boardApply.entity.Application;
import plming.board.boardApply.repository.ApplicationRepository;
import plming.board.board.dto.response.BoardListResponseDto;
import plming.board.board.dto.request.BoardRequestDto;
import plming.board.board.dto.response.BoardResponseDto;
import plming.board.board.dto.response.UserBoardListResponseDto;
import plming.board.boardApply.service.ApplicationService;
import plming.board.boardTag.service.BoardTagService;
import plming.board.boardComment.service.CommentService;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.board.board.repository.BoardRepository;
import plming.board.boardTag.repository.BoardTagRepository;
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
    private final ApplicationRepository applicationRepository;
    private final BoardTagService boardTagService;
    private final UserService userService;
    private final ApplicationService applicationService;
    private final CommentService commentService;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final BoardRequestDto params, final Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        Board entity = boardRepository.save(params.toEntity(user));
        List<Long> boardTagIds = params.getTagIds();
        boardTagService.save(boardTagIds, entity);

        // 게시글 작성자는 자동으로 참여 승인
        applicationRepository.save(new Application(user, entity, "승인"));

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
     * 게시글 모집 상태 자동 업데이트
     */
    @Transactional
    public Long updateBoardStatus(final Long id) {

        // 신청 인원이 가득 찼을 경우 자동으로 모집 완료 상태로 변경
        if(!applicationService.isMaxNum(id)) {
            applicationRepository.updateBoardStatus(id, "모집 완료");
        }

        return id;
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

    /**
     * 삭제되지 않은 게시글 리스트 조회
     */
    public Page<BoardListResponseDto> findAllByDeleteYn(final Pageable pageable) {

        Page<Board> list = boardRepository.findAllPageSort(pageable);
        return getBoardListResponseFromPage(list, pageable);
    }

    /**
     * 사용자 Id 기준 댓글 단 게시글 리스트 + 작성한 게시글 리스트 + 신청한 게시글 리스트 반환
     */
    @Transactional
    public UserBoardListResponseDto findAllByUserId(final Long userId) {

        return UserBoardListResponseDto.builder()
                .write(findBoardByUserId(userId))
                .comment(findCommentBoardByUserId(userId))
                .apply(findAppliedBoard(userId))
                .build();
    }

    /**
     * 게시글 리스트 조회 - (사용자 ID 기준)
     */
    private List<BoardListResponseDto> findBoardByUserId(final Long userId) {

        List<Board> list = boardRepository.findAllByUserId(userId);
        return getBoardListResponseFromBoardList(list);
    }

    /**
     * 댓글 단 게시글 리스트 조회 - (사용자 Id 기준)
     */
    private List<BoardListResponseDto> findCommentBoardByUserId(final Long userId) {

        List<Long> boardId = commentService.findCommentBoardByUserId(userId);
        List<Board> boardList = boardId.stream().map(id -> boardRepository.findById(id).get()).collect(Collectors.toList());

        return getBoardListResponseFromBoardList(boardList);
    }

    /**
     * 신청 게시글 리스트 조회 - (사용자 ID 기준)
     */
    @Transactional
    public List<BoardListResponseDto> findAppliedBoard(final Long userId) {

        return getBoardListResponseFromBoardList(applicationService.findAppliedBoard(userId));
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
     * 게시글 신청 하기
     */
    @Transactional
    public String apply(final Long boardId, final Long userId) {

        return applicationService.save(boardId, userId);
    }

    /**
     * 신청 사용자 리스트 조회 + 참여자 리스트 조회 - (사용자 ID 기준)
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
    public void updateAppliedStatus(final Long boardId, final Long userId, final String nickname, final String status) {

        if(boardRepository.getById(boardId).getUser().getId().equals(userId)) {

            applicationService.updateAppliedStatus(boardId, nickname, status);
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
     * 지원 취소하기 - application 테이블에서 데이터 삭제
     */
    public void cancelApplied(final Long boardId, final Long userId) {

        applicationService.cancelApplied(boardId, userId);
    }

    /**
     * 지원 상태 확인하기
     */
    public String findApplicationStatus(final Long boardId, final Long userId) {

        Application application = applicationService.findApplication(boardId, userId);

        if(application == null) {
            return "미신청";
        } else{
            return application.getStatus();
        }
    }

    /**
     * 각 게시글의 태그 이름 조회 후 BoardListResponseDto 반환
     */
    @Transactional
    public Page<BoardListResponseDto> getBoardListResponseFromPage(Page<Board> list, Pageable pageable) {

        List<BoardListResponseDto> result = new ArrayList<>();

        List<Board> boards = list.getContent();
        for (Board post : boards) {
            Integer participantNum = applicationService.countParticipantNum(post.getId());
            result.add(new BoardListResponseDto(post, participantNum, boardTagService.findTagNameByBoardId(post.getId())));
        }

        return PageableExecutionUtils.getPage(result, pageable, list::getTotalElements);
    }

    /**
     * 각 게시글의 태그 이름 조회 후 BoardListResponseDto 반환
     */
    public List<BoardListResponseDto> getBoardListResponseFromBoardList(List<Board> list) {

        List<BoardListResponseDto> result = new ArrayList<>();
        for (Board board : list) {
            Integer participantNum = applicationService.countParticipantNum(board.getId());
            result.add(new BoardListResponseDto(board, participantNum, boardTagService.findTagNameByBoardId(board.getId())));
        }

        return result;
    }
}