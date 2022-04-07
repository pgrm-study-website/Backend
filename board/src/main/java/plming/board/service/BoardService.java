package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public String update(final Long id, final BoardRequestDto params) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if(entity.getStatus().equals("모집 완료")) {
            return "모집 완료";
        }

        if((!(params.getParticipantMax() == null)) && (params.getParticipantMax() < countParticipantNum(id))) {
            return "인원 수";
        }

        entity.update(params.getParticipantMax(), params.getTitle(), params.getContent(), params.getCategory(), params.getStatus(), params.getPeriod());
        boardTagRepository.deleteAllByBoardId(id);
        boardTagService.save(params.getTagIds(), entity);

        return entity.getId().toString();
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(final Long id) {

        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.delete();
        boardTagRepository.deleteAllByBoardId(id);

    }

    /**
     * 게시글 리스트 조회 - (사용자 ID 기준)
     */
    public List<BoardListResponseDto> findAllByUserId(final Long userId) {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAllByUserId(userId, sort);
        return getBoardListResponse(list);
    }

    /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
     */
    public Map<String, Object> findAllByDeleteYn(final char deleteYn) {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAllByDeleteYn(deleteYn, sort);

        Map<String, Object> result = new HashMap<>(2);
        result.put("postCount", list.size());
        result.put("posts", getBoardListResponse(list));

        return result;
    }

    /**
     * 각 게시글의 태그 이름 조회 후 ResponseDto 반환
     */
    public List<BoardResponseDto> getBoardResponse(List<Board> list) {
        List<BoardResponseDto> result = new ArrayList<BoardResponseDto>();

        for (Board post : list) {
                List<String> tagName = boardTagService.findTagNameByBoardId(post.getId());
                Integer participantNum = applicationService.countParticipantNum(post.getId());
                result.add(new BoardResponseDto(post, participantNum, tagName));
        }
        return result;
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
        entity.increaseCount();
        List<String> boardTagName = boardTagService.findTagNameByBoardId(id);

        return new BoardResponseDto(entity, applicationService.countParticipantNum(id) ,boardTagName);
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
    public List<BoardListResponseDto> findAppliedBoardByUserId(final Long userId) {

        List<Board> appliedBoards = applicationService.findAppliedBoardByUserId(userId);

        return getBoardListResponse(appliedBoards);
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    public List<UserListResponseDto> findAppliedUserByBoardId(final Long boardId) {

        List<User> appliedUsers = applicationService.findAppliedUserByBoardId(boardId);

        return appliedUsers.stream().map(User::getId).map(userService::getUserList).collect(Collectors.toList());
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
    public String updateAppliedStatus(final Long boardId, final Long userId, final String status) {

        Application application = applicationService.updateAppliedStatus(boardId, userId, status);

        return application.getStatus();
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

