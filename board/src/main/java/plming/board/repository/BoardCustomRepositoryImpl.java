package plming.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
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
    public Page<Board> findAllByUserId(Long userId, Pageable pageable) {

        // content를 가져오는 쿼리
        List<Board> query = jpaQueryFactory
                .select(board).from(board)
                .leftJoin(board.boardTags, boardTag)
                .fetchJoin()
                .where(board.user.id.eq(userId).and(board.deleteYn.eq('0')))
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

        if(params.getKeyword() == null) {

            // content를 가져오는 쿼리
            List<Board> query = jpaQueryFactory
                    .select(board).from(board)
                    .leftJoin(board.boardTags, boardTag)
                    .fetchJoin()
                    .where(keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct()
                    .orderBy(board.id.desc(), board.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            // count만 가져오는 쿼리
            JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                    .leftJoin(board.boardTags, boardTag)
                    .where(keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct();

            return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());

        } else if(params.getSearchType().equals("title")) {

            // content를 가져오는 쿼리
            List<Board> query = jpaQueryFactory
                    .select(board).from(board)
                    .leftJoin(board.boardTags, boardTag)
                    .fetchJoin()
                    .where(keywordInTitle(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct()
                    .orderBy(board.id.desc(), board.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            // count만 가져오는 쿼리
            JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                    .leftJoin(board.boardTags, boardTag)
                    .where(keywordInTitle(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct();

            return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());

        } else if(params.getSearchType().equals("content")) {

            // content를 가져오는 쿼리
            List<Board> query = jpaQueryFactory
                    .select(board).from(board)
                    .leftJoin(board.boardTags, boardTag)
                    .fetchJoin()
                    .where(keywordInContent(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct()
                    .orderBy(board.id.desc(), board.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            // count만 가져오는 쿼리
            JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                    .leftJoin(board.boardTags, boardTag)
                    .where(keywordInContent(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct();

            return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());

        } else {

            // content를 가져오는 쿼리
            List<Board> query = jpaQueryFactory
                    .select(board).from(board)
                    .leftJoin(board.boardTags, boardTag)
                    .fetchJoin()
                    .where(keywordInTitleAndContent(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct()
                    .orderBy(board.id.desc(), board.createDate.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            // count만 가져오는 쿼리
            JPQLQuery<Board> count = jpaQueryFactory.selectFrom(board)
                    .leftJoin(board.boardTags, boardTag)
                    .where(keywordInTitleAndContent(params.getKeyword()), keywordInCategory(params.getCategory())
                            , keywordInStatus(params.getStatus()), keywordInTag(params.getTagIds())
                            , isPeriod(params.getPeriod()), isParticipantMax(params.getParticipantMax())
                            , board.deleteYn.eq('0'))
                    .distinct();

            return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());
        }
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