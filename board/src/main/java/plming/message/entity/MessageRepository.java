package plming.message.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.message.repository.MessageCustomRepository;

public interface MessageRepository extends JpaRepository<Message,Long>, MessageCustomRepository {

}
