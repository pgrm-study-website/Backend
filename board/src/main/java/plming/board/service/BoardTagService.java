package plming.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Board;
import plming.board.entity.BoardTag;
import plming.board.entity.BoardTagRepository;
import plming.tag.entity.Tag;
import plming.tag.entity.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardTagService {

    private final BoardTagRepository boardTagRepository;
    private final TagRepository tagRepository;

    /**
     * 게시글 태그 저장
     */
    @Transactional
    public void save(List<Long> tagIdList, Board entity) {

        tagIdList.stream().map(tagId -> BoardTag.builder()
                .board(entity)
                .tag(tagRepository.getById(tagId))
                .build()).forEach(boardTagRepository::save);
    }

    /**
     * 게시글 id로 태그 이름 조회
     */
    public List<String> findTagNameByBoardId (final Long id) {

        List<BoardTag> boardTagList = boardTagRepository.findAllByBoardId(id);
        List<String> boardTagNameList = boardTagList.stream().map(BoardTag::getTag).map(Tag::getName).collect(Collectors.toList());

        return boardTagNameList;
    }


}
