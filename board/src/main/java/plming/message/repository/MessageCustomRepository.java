package plming.message.repository;

import plming.message.entity.Message;
import plming.user.entity.User;

import java.util.List;

public interface MessageCustomRepository {
    List<Message> findMessageGroupByUser(User user);
    List<Message> findMessageByUserAndOther(User user, User other);
}
