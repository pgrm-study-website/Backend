package plming.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.board.entity.Application;
import plming.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

import static plming.board.entity.QApplication.application;

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
    public List<Board> findAppliedBoardByUserId(Long userId) {
        return jpaQueryFactory.select(application.board).from(application)
                .where(application.user.id.eq(userId))
                .fetch();
    }

    /**
     * 신청 사용자 리스트 조회 - (게시글 ID 기준)
     */
    @Override
    public List<User> findAppliedUserByBoardId(Long boardId) {

        return jpaQueryFactory.select(application.user).from(application)
                .where(application.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public List<User> findParticipantByBoardId(Long boardId) {

        return jpaQueryFactory.select(application.user).from(application)
                .where(application.board.id.eq(boardId), application.status.eq("승인"))
                .fetch();
    }

    @Override
    public List<Application> findApplication(Long boardId, Long userId) {

        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<Application> updateAppliedStatus(Long boardId, Long userId, String status) {
        jpaQueryFactory.update(application)
                .set(application.status, status)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .execute();
        return jpaQueryFactory.selectFrom(application)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .fetch();

    }

    @Override
    public void cancelApplied(Long boardId, Long userId) {
        jpaQueryFactory.delete(application)
                .where(application.board.id.eq(boardId), application.user.id.eq(userId))
                .execute();
    }


}
