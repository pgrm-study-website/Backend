package plming.board.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
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
    public List<Board> searchTitle(String keyword) {

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            builder.and(board.title.contains(keyword));
        }

        return jpaQueryFactory.selectFrom(board)
                .where(builder).fetch();
    }

    @Override
    public List<Board> searchContent(String keyword) {

        BooleanBuilder builder = new BooleanBuilder();

        if (keyword != null) {
            builder.and(board.content.contains(keyword));
        }

        return jpaQueryFactory.selectFrom(board)
                .where(builder).fetch();
    }
}