package plming.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

import static plming.board.entity.QApplication.application;
import static plming.user.entity.QUser.user;

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
    public List<Board> findAppliedBoard(Long userId) {
        return jpaQueryFactory.select(application.board).from(application)
                .where(application.user.id.eq(userId), application.board.deleteYn.eq('0'))
                .orderBy(application.board.id.desc(), application.board.createDate.desc())
                .fetch();
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    @Override
    public List<Application> findAppliedUserByBoardId(Long boardId) {

        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId), application.board.deleteYn.eq('0'))
                .fetch();
    }

    /**
     * 참가자 리스트 조회 - (게시글 ID 기준)
     */
    @Override
    public List<User> findParticipantByBoardId(Long boardId) {

        return jpaQueryFactory.select(application.user).from(application)
                .where(application.board.id.eq(boardId), application.status.eq("승인"),
                        application.user.deleteYn.eq('0'))
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
    @Transactional
    public Application updateAppliedStatus(Long boardId, String nickname, String status) {

        Long userId = jpaQueryFactory.select(user.id).from(user).where(user.nickname.eq(nickname)).fetchOne();

        jpaQueryFactory.update(application)
                .set(application.status, status)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
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