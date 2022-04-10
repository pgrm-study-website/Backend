package plming.board.repository;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import plming.board.entity.Application;
import plming.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

import static plming.board.entity.QApplication.application;
import static plming.board.entity.QBoard.board;

@Repository
public class ApplicationCustomRepositoryImpl implements ApplicationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ApplicationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {

        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 신청 게시글 리스트 조회 - (사용자 Id 기준)
     */
    @Override
    public Page<Board> findAppliedBoardByUserId(Long userId, Pageable pageable) {

        // content를 가져오는 쿼리
        List<Board> query = jpaQueryFactory
                .select(application.board).from(application)
                .where(application.user.id.eq(userId))
                .orderBy(board.id.desc(), board.createDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // count만 가져오는 쿼리
        JPQLQuery<Board> count = jpaQueryFactory.selectFrom(application.board)
                .where(application.user.id.eq(userId));

        return PageableExecutionUtils.getPage(query, pageable, () -> count.fetchCount());
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    @Override
    public List<Application> findAppliedUserByBoardId(Long boardId) {

        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId))
                .fetch();
    }

    /**
     * 참가자 리스트 조회 - (게시글 ID 기준)
     */
    @Override
    public List<User> findParticipantByBoardId(Long boardId) {

        return jpaQueryFactory.select(application.user).from(application)
                .where(application.board.id.eq(boardId), application.status.eq("승인"))
                .fetch();
    }

    /**
     * 게시글 신청 조회
     */
    @Override
    public Application findApplication(Long boardId, Long userId) {

        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .fetchOne();
    }

    @Override
    public Application updateAppliedStatus(Long boardId, String nickname, String status) {
        jpaQueryFactory.update(application)
                .set(application.status, status)
                .where(application.board.id.eq(boardId), application.user.nickname.eq(nickname))
                .execute();
        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId), application.user.nickname.eq(nickname))
                .fetchOne();
    }

    @Override
    public void cancelApplied(Long boardId, Long userId) {
        jpaQueryFactory.delete(application)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .execute();
    }
}
