package plming.message.dto;

import lombok.Getter;
import plming.message.entity.Message;

import java.time.LocalDateTime;

@Getter
public class MessageListResponseDto {
    private Long otherPersonId;
    private String otherPersonNickname;
    private String content;
    private LocalDateTime createDate;

    public MessageListResponseDto(Message message, Long userId){
        if (message.getSender().getId().equals(userId)){
            this.otherPersonId = message.getReceiver().getId();
            this.otherPersonNickname = message.getReceiver().getNickname();
        }else {
            this.otherPersonId = message.getSender().getId();
            this.otherPersonNickname = message.getSender().getNickname();
        }
        this.content = message.getContent();
        this.createDate = message.getCreateDate();
    }
}
