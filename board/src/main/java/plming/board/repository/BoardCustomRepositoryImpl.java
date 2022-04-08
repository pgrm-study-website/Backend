package plming.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import plming.board.entity.Board;
import plming.board.entity.BoardTag;

import static org.springframework.util.StringUtils.trimAllWhitespace;
import static plming.board.entity.QBoard.board;
import static plming.board.entity.QBoardTag.boardTag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Board> searchTitle(String keyword) {

        return jpaQueryFactory.selectFrom(board)
                    .where(keywordInTitle(keyword)).fetch();
    }

    @Override
    public List<Board> searchContent(String keyword) {

        return jpaQueryFactory.selectFrom(board)
                .where(keywordInContent(keyword)).fetch();
    }

    @Override
    public List<Board> searchCategory(List<String> keywords) {

        return jpaQueryFactory.selectFrom(board)
                .where(keywordInCategory(keywords)).fetch();
    }

    @Override
    public List<Board> searchTag(List<Integer> keywords) {

        return jpaQueryFactory.select(board).from(boardTag)
                .where(keywordInTag(keywords)).distinct().fetch();
    }

    @Override
    public List<Board> searchTitleAndContent(String keyword) {

        return jpaQueryFactory.selectFrom(board)
                .where(keywordInTitleAndContent(keyword))
                .distinct().fetch();
    }

    @Override
    public List<Board> searchTitleAndCategory(String keyword, List<String> categories) {

        return jpaQueryFactory.selectFrom(board)
                .where(keywordInTitleAndCategory(keyword, categories))
                .distinct().fetch();
    }

    @Override
    public List<Board> searchTitleAndTag(String keyword, List<Integer> tags) {

        return jpaQueryFactory.select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(keywordInTitle(keyword), keywordInTag(tags))
                .distinct()
                .fetch();
    }

    @Override
    public List<Board> searchContentAndCategory(String keyword, List<String> categories) {

        return jpaQueryFactory.selectFrom(board)
                .where(keywordInContentAndCategory(keyword, categories))
                .distinct().fetch();
    }

    @Override
    public List<Board> searchContentAndTag(String keyword, List<Integer> tags) {

        return jpaQueryFactory.select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(keywordInContent(keyword), keywordInTag(tags))
                .distinct()
                .fetch();
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

    private BooleanExpression keywordInTitleAndCategory(String keyword, List<String> categories) {

        return keywordInTitle(keyword).and(keywordInCategory(categories));
    }

    private BooleanExpression keywordInContentAndCategory(String keyword, List<String> categories) {

        return keywordInContent(keyword).and(keywordInCategory(categories));
    }


}