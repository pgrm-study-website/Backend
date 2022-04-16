package plming.message.repository;

import plming.message.entity.Message;

import java.util.List;

public interface MessageCustomRepository {
    List<Message> findMessageAllListByUserId(Long userId);
    List<Message> findMessageListByUserIdAndOtherId(Long userId, Long otherId);
}
