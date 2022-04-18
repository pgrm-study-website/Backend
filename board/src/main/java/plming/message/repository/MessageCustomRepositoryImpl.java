package plming.message.repository;

import com.querydsl.jpa.JPAExpressions;
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

    /*
     * QueryDsl을 통해 senderId와 receiverId를 기준으로 메시지들을 그룹핑하여 조회하고,
     * 조회된 리스트에서 (senderId, receiverId)를 순서가 없는 조합으로 다시 그룹핑한다.
     * 이후, 그룹핑된 Map객체에서 createDate를 기준으로 정렬한다.
     */
    @Override
    public List<Message> findMessageAllListByUser(User user){
        List<Message> unOrganizedList = jpaQueryFactory.selectFrom(message)
                .where(message.id.in(
                        JPAExpressions.select(message.id.max())
                                .from(message)
                                .where(
                                        message.sender.eq(user).and(message.senderDeleteYn.eq('0'))
                                                .or(message.receiver.eq(user).and(message.receiverDeleteYn.eq('0'))))
                                .groupBy(message.sender,message.receiver)
                )).fetch();

        Map<Long, List<Message>> messageGroup = new HashMap<>();
        for(Message m : unOrganizedList){
            Long id = m.getSender().getId() == user.getId() ? m.getReceiver().getId() : m.getSender().getId();
            if(messageGroup.containsKey(id)){
                messageGroup.get(id).add(m);
            }else{
                messageGroup.put(id,new ArrayList<>());
                messageGroup.get(id).add(m);
            }
        }

        List<Message> messageList = new ArrayList<>();
        messageGroup.forEach((key,unSortedMessageList)->{
            unSortedMessageList.sort(Collections.reverseOrder());
            messageList.add(unSortedMessageList.get(0));
            }
        );
        messageList.sort(Collections.reverseOrder());
        return messageList;
    }

    @Override
    public List<Message> findMessageListByUserAndOther(User user, User other){
        return jpaQueryFactory.selectFrom(message)
                .where(
                        message.sender.eq(user).and(message.receiver.eq(other)).and(message.senderDeleteYn.eq('0'))
                                .or(message.sender.eq(other).and(message.receiver.eq(user)).and(message.receiverDeleteYn.eq('0')))
                )
                .orderBy(message.id.desc())
                .fetch();
    }
}
