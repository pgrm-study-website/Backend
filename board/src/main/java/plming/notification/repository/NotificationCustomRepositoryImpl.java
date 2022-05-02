package plming.notification.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.notification.entity.Notification;

import java.util.List;

import static plming.notification.entity.QNotification.notification;
import static plming.user.entity.QUser.user;

@Repository
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public NotificationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {

        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Notification> findAllByUserId(Long userId) {
        return jpaQueryFactory.selectFrom(notification)
                .leftJoin(notification.receiver, user)
                .fetchJoin()
                .where(notification.receiver.id.eq(userId))
                .orderBy(notification.id.desc())
                .fetch();
    }

    @Override
    public Long countUnReadNotification(Long userId) {
        return jpaQueryFactory.selectFrom(notification)
                .leftJoin(notification.receiver, user)
                .fetchJoin()
                .where(notification.receiver.id.eq(userId), notification.isRead.eq(false))
                .orderBy(notification.id.desc())
                .stream().count();
    }

    @Override
    public void deleteAllByUserId(Long userId) {

        jpaQueryFactory.delete(notification)
                .where(notification.receiver.id.eq(userId))
                .execute();
    }
}
