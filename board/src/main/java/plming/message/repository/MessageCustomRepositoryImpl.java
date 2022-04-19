package plming.message.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.message.entity.Message;
import plming.user.entity.User;

import java.util.*;

import static plming.message.entity.QMessage.message;

@Repository
public class MessageCustomRepositoryImpl implements MessageCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public MessageCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Message> findMessageGroupByUser(User user){
        JPQLQuery<Long> subQuery = JPAExpressions.select(message.id.max())
                .from(message)
                .where(sendMessageCond(user).or(receiveMessageCond(user)))
                .groupBy(Expressions.stringTemplate("least(sender_id,receiver_id),greatest(sender_id,receiver_id)"));

        return jpaQueryFactory.selectFrom(message)
                .where(message.id.in(subQuery))
                .orderBy(message.id.desc())
                .fetch();
    }

    @Override
    public List<Message> findMessageByUserAndOther(User user, User other){
        return jpaQueryFactory.selectFrom(message)
                .where(sendMessageCondWithOther(user,other).or(receiveMessageCondWithOther(user, other)))
                .orderBy(message.id.desc())
                .fetch();
    }

    private BooleanBuilder sendMessageCond(User user){
        return new BooleanBuilder()
                .and(senderUserEq(user))
                .and(senderDeleteYnEq());
    }

    private BooleanBuilder receiveMessageCond(User user){
        return new BooleanBuilder()
                .and(receiverUserEq(user))
                .and(receiverDeleteYnEq());
    }

    private BooleanBuilder sendMessageCondWithOther(User user, User other){
        return new BooleanBuilder()
                .and(senderUserEq(user))
                .and(receiverUserEq(other))
                .and(senderDeleteYnEq());
    }

    private BooleanBuilder receiveMessageCondWithOther(User user, User other){
        return new BooleanBuilder()
                .and(senderUserEq(other))
                .and(receiverUserEq(user))
                .and(receiverDeleteYnEq());
    }

    private BooleanExpression senderUserEq(User user){
        return user != null ? message.sender.eq(user)  : null;
    }

    private BooleanExpression receiverUserEq(User user){
        return user != null ? message.receiver.eq(user)  : null;
    }

    private BooleanExpression senderDeleteYnEq(){
        return message.senderDeleteYn.eq('0');
    }

    private BooleanExpression receiverDeleteYnEq(){
        return message.receiverDeleteYn.eq('0');
    }
}
