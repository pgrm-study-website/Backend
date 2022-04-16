package plming.message.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import plming.message.entity.Message;

import java.util.*;

import static plming.message.entity.QMessage.message;

@Repository
public class MessageCustomRepositoryImpl implements MessageCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public MessageCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Message> findMessageAllListByUserId(Long userId){
        List<Message> list = jpaQueryFactory.selectFrom(message)
                .where(message.id.in(
                        JPAExpressions.select(message.id.max())
                                .from(message)
                                .where(isSenderIdEq(userId)
                                        .and(message.sender.id.eq(userId).or(message.receiver.id.eq(userId))))
                                .groupBy(message.sender,message.receiver)
                ))
                .fetch();
        Map<Long, List<Message>> map = new HashMap<>();
        for(Message item : list){
            Long id = item.getSender().getId() == userId ? item.getReceiver().getId() : item.getSender().getId();
            if(map.containsKey(id)){
                map.get(id).add(item);
            }else{
                map.put(id,new ArrayList<>());
                map.get(id).add(item);
            }
        }
        List<Message> returnList = new ArrayList<>();
        map.forEach((key,value)->{
            Message max = null;
            for(Message i : value) {
                if(max != null){
                    Math.max(i.getId(), max.getId());
                }
                max = i;
            }
            returnList.add(max);
            }
        );
        Collections.sort(returnList,Collections.reverseOrder());
        return returnList;
    }

    @Override
    public List<Message> findMessageListByUserIdAndOtherId(Long userId, Long otherId){
        return jpaQueryFactory.selectFrom(message)
                .where(message.sender.id.eq(userId).and(message.receiver.id.eq(otherId))
                        .or(message.sender.id.eq(otherId).and(message.receiver.id.eq(userId)))
                ).orderBy(message.id.desc())
                .fetch();
    }

    // 사용자가 삭제하지 않은 메시지인지 확인
    public BooleanExpression isSenderIdEq(Long userId){
        if(message.sender.id.equals(userId)){
            return message.senderDeleteYn.eq('0');
        }else{
            return message.receiverDeleteYn.eq('0');
        }
    }

}
