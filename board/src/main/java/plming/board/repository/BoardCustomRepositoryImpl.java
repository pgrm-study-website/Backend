package plming.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import plming.board.dto.SearchRequestDto;
import plming.board.entity.Board;
import plming.board.entity.BoardTag;

import static plming.board.entity.QBoard.board;
import static plming.board.entity.QBoardTag.boardTag;

import java.util.List;

@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {

        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Board> findAllByDeleteYn(char deleteYn, Sort sort) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.deleteYn.eq(deleteYn))
                .fetch();
    }

    @Override
    public List<Board> findAllByUserId(Long userId, Sort sort) {
        return jpaQueryFactory.selectFrom(board)
                .where(board.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<BoardTag> findAllByBoardId(Long boardId) {
        return jpaQueryFactory.selectFrom(boardTag)
                .where(boardTag.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public void deleteAllByBoardId(Long boardId) {
        jpaQueryFactory.delete(boardTag)
                .where(boardTag.board.id.eq(boardId))
                .execute();
    }

    @Override
    public List<Board> searchAllCondition(SearchRequestDto params) {

        return jpaQueryFactory.select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(keywordInTitleAndContent(params.getKeyword()).and(keywordInCategory(params.getCategories()))
                        .and(keywordInStatus(params.getStatus())).and(keywordInTag(params.getTags())))
                .distinct()
                .fetch();
    }

    private BooleanBuilder keywordInStatus(List<String> keywords) {

        if(keywords != null ) {
            BooleanBuilder builder = new BooleanBuilder();
            keywords.stream().map(board.status::eq).forEach(builder::or);

            return builder;
        }

        return null;
    }

    private BooleanExpression keywordInTitle(String keyword) {

        return StringUtils.hasText(keyword) ? board.title.contains(keyword) : null;
    }

    private BooleanExpression keywordInContent(String keyword) {

        return StringUtils.hasText(keyword) ? board.content.contains(keyword) : null;
    }

    private BooleanBuilder keywordInCategory(List<String> keywords) {

        if(keywords != null ) {
            BooleanBuilder builder = new BooleanBuilder();
            keywords.stream().map(board.category::eq).forEach(builder::or);

            return builder;
        }

        return null;
    }

    private BooleanBuilder keywordInTag(List<Integer> keywords) {

        if (keywords != null) {
            BooleanBuilder builder = new BooleanBuilder();
            keywords.stream().map(keyword -> boardTag.tag.id.eq(Long.valueOf(keyword))).forEach(builder::or);

            return builder;
        }

        return null;
    }

    private BooleanExpression keywordInTitleAndContent(String keyword) {

        return keywordInTitle(keyword).or(keywordInContent(keyword));
    }
}