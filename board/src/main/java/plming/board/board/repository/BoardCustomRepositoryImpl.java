package plming.board.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import plming.board.boardSearch.dto.SearchRequestDto;
import plming.board.board.entity.Board;
import plming.board.boardTag.entity.BoardTag;
import plming.exception.CustomException;
import plming.exception.ErrorCode;

import static plming.board.board.entity.QBoard.board;
import static plming.board.boardTag.entity.QBoardTag.boardTag;

import java.util.List;

@Repository
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public BoardCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {

        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Board> findAllPageSort(Pageable pageable) {

        // content를 가져오는 쿼리
        List<Board> query = jpaQueryFactory
                .select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(board.deleteYn.eq('0'))
                .orderBy(board.id.desc(), board.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count만 가져오는 쿼리
        JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.boardTags, boardTag)
                .where(board.deleteYn.eq('0'));

        return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());
    }

    @Override
    public List<Board> findAllByUserId(Long userId) {

        return jpaQueryFactory
                .select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(board.user.id.eq(userId).and(board.deleteYn.eq('0')))
                .distinct()
                .orderBy(board.id.desc(), board.createDate.desc())
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
    public Page<Board> searchAllCondition(SearchRequestDto params, Pageable pageable) {

        if(params.getSearchType() != null && params.getSearchType().equals("viewCnt")) {

            // content를 가져오는 쿼리
            List<Board> query = jpaQueryFactory
                    .select(board).from(board)
                    .leftJoin(board.boardTags, boardTag)
                    .fetchJoin()
                    .where(board.deleteYn.eq('0'))
                    .distinct()
                    .orderBy(board.viewCnt.desc(), board.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            // count만 가져오는 쿼리
            JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                    .leftJoin(board.boardTags, boardTag)
                    .where(board.deleteYn.eq('0'))
                    .distinct();

            return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());
        }

        if(params.getKeyword() == null) {
            return resultSearch(params, pageable, null);

        } else if(params.getSearchType().equals("title")) {
            return resultSearch(params, pageable, keywordInTitle(params.getKeyword()));

        } else if(params.getSearchType().equals("content")) {
            return resultSearch(params, pageable, keywordInContent(params.getKeyword()));

        } else if(params.getSearchType().equals("titlecontent")){
            return resultSearch(params, pageable, keywordInTitleAndContent(params.getKeyword()));

        } else {
            throw new CustomException(ErrorCode.BAD_SEARCH);
        }
    }

    private Page<Board> resultSearch(SearchRequestDto params, Pageable pageable, BooleanExpression condition) {

        // content를 가져오는 쿼리
        List<Board> query = jpaQueryFactory
                .select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(keywordInCategory(params.getCategory())
                        , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                        , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                        , board.deleteYn.eq('0'), condition)
                .distinct()
                .orderBy(board.id.desc(), board.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count만 가져오는 쿼리
        JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                .leftJoin(board.boardTags, boardTag)
                .where(condition, keywordInCategory(params.getCategory())
                        , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                        , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                        , board.deleteYn.eq('0'))
                .distinct();

        return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());
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

        return keyword != null ? board.title.contains(keyword) : null;
    }

    private BooleanExpression keywordInContent(String keyword) {

        return keyword != null ? board.content.contains(keyword) : null;
    }

    private BooleanExpression keywordInTitleAndContent(String keyword) {

        return keyword != null ? keywordInTitle(keyword).or(keywordInContent(keyword)) : null;
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

    private BooleanExpression isPeriod(List<Integer> period) {

        return period != null ? board.period.between(period.get(0), period.get(1)) : null;
    }

    private BooleanExpression isParticipantMax(List<Integer> participantMax) {

        return participantMax != null ? board.participantMax.between(participantMax.get(0), participantMax.get(1)) : null;
    }
}