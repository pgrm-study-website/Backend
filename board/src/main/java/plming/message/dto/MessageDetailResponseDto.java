package plming.message.dto;

import lombok.Getter;
import plming.message.entity.Message;

import java.time.LocalDateTime;

@Getter
public class MessageDetailResponseDto {
    private Long messageId;
    private String type;
    private String content;
    private LocalDateTime createDate;

    public MessageDetailResponseDto(Message message, Long userId){
        this.messageId = message.getId();
        this.content = message.getContent();
        this.createDate = message.getCreateDate();
        if(message.getSender().getId().equals(userId)){
            this.type = "send";
        }else{
            this.type = "receive";
        }
    }
}
