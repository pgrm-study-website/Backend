package plming.message.dto;

import lombok.Getter;
import plming.message.entity.Message;
import plming.user.entity.User;

@Getter
public class MessageRequestDto {
    private Long userId;
    private Long otherId;
    private String content;

    public Message toEntity(User sender, User receiver){
        return Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .build();
    }
}
