package plming.board.model;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.dto.BoardRequestDto;
import plming.board.dto.BoardResponseDto;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.entity.BoardTagRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardTagRepository boardTagRepository;
    private final BoardTagService boardTagService;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(final BoardRequestDto params) {

        User user = userRepository.getById(params.getUserId());
        Board entity = boardRepository.save(params.toEntity(user));
        List<Long> boardTagIds = params.getBoardTagIds();
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
        boardTagService.save(params.getBoardTagIds(), entity);

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
<<<<<<< HEAD
=======
     */
    public List<BoardResponseDto> findAll() {

        Sort sort = Sort.by(DESC, "id", "createDate");
        List<Board> list = boardRepository.findAll(sort);
        return getTagName(list);
    }

    /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
>>>>>>> 632d22e... Feat: 태그 테이블 연관 관계 매핑
     */
    public List<BoardResponseDto> findAll() {

        Sort sort = Sort.by(DESC, "id", "createDate");
<<<<<<< HEAD
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
=======
        List<Board> list = boardRepository.findAllByDeleteYn(deleteYn, sort);
        return getTagName(list);
>>>>>>> 632d22e... Feat: 태그 테이블 연관 관계 매핑
    }

    /**
     * 게시글 리스트 조회 - (삭제 여부 기준)
     */
    public List<BoardResponseDto> findAllByDeleteYn(final char deleteYn) {

        Sort sort = Sort.by(DESC, "id", "createDate");
<<<<<<< HEAD
        List<Board> list = boardRepository.findAllByDeleteYn(deleteYn, sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
=======
        List<Board> list = boardRepository.findAllByUserId(userId, sort);
        return getTagName(list);
    }

    /**
     * 각 게시글의 태그 이름 조회
     */
    private List<BoardResponseDto> getTagName(List<Board> list) {
        List<BoardResponseDto> result = new ArrayList<BoardResponseDto>();
        // List<List<String>> tags= list.stream().map(Board::getId).map(boardTagService::findTagNameByBoardId).collect(Collectors.toList());
        for (Board post : list) {
                List<String> tagName = boardTagService.findTagNameByBoardId(post.getId());
                result.add(new BoardResponseDto(post, tagName));
        }
        return result;
>>>>>>> 632d22e... Feat: 태그 테이블 연관 관계 매핑
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
}
