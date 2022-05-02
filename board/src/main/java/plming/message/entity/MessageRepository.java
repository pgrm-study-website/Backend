package plming.message.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.message.repository.MessageCustomRepository;
import plming.user.entity.User;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> , MessageCustomRepository {
    List<Message> findAllBySenderOrReceiver(User sender,User receiver);
}
