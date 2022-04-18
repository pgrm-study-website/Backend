package plming.message.repository;

import plming.message.entity.Message;
import plming.user.entity.User;

import java.util.List;

public interface MessageCustomRepository {
    List<Message> findMessageAllListByUser(User user);
    List<Message> findMessageListByUserAndOther(User user, User other);
}
