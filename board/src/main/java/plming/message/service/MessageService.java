package plming.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import plming.event.MessageCreateEvent;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.message.dto.MessageGroupResponseDto;
import plming.message.dto.MessageRequestDto;
import plming.message.dto.MessageDetailResponseDto;
import plming.message.entity.Message;
import plming.message.entity.MessageRepository;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;
import plming.user.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    public List<MessageGroupResponseDto> getMessageGroupList(Long userId){
        User user = userService.getUserById(userId);
        List<Message> messageList = messageRepository.findMessageGroupByUser(user);
        return messageList.stream().map(m -> new MessageGroupResponseDto(m, userId)).collect(Collectors.toList());
    }

    public List<MessageDetailResponseDto> getMessageDetailList(Long userId, Long otherId){
        User user = userService.getUserById(userId);
        User other = userService.getUserById(otherId);
        List<Message> messageList = messageRepository.findMessageByUserAndOther(user,other);
        return messageList.stream().map(m -> new MessageDetailResponseDto(m,userId)).collect(Collectors.toList());
    }

    @Transactional
    public MessageDetailResponseDto postMessage(MessageRequestDto messageRequestDto){
        User user = userService.getUserById(messageRequestDto.getUserId());
        User other = userService.getUserById(messageRequestDto.getOtherId());
        Message message = messageRepository.save(messageRequestDto.toEntity(user,other));
        eventPublisher.publishEvent(new MessageCreateEvent(message, message.getReceiver(), NotificationType.message));
        return new MessageDetailResponseDto(message,messageRequestDto.getUserId());
    }

    @Transactional
    public void deleteAllMessage(Long userId, Long otherId){
        User user = userService.getUserById(userId);
        User other = userService.getUserById(otherId);

        List<Message> messageList = messageRepository.findMessageByUserAndOther(user,other);
        for(Message m : messageList){
            if(m.getSender().getId().equals(userId)){
                m.setSenderDeleted();
            }else{
                m.setReceiverDeleted();
            }
        }
        messageList.stream().filter(Message::hasToBeDeleted).forEach(m -> messageRepository.delete(m));
    }

    @Transactional
    public void deleteMessage(Long userId, Long messageId){
        User user = userService.getUserById(userId);
        Message message = messageRepository.findById(messageId).orElseThrow(()-> new CustomException(ErrorCode.MESSAGE_NOT_FOUND));

        if(message.getSender().getId().equals(user.getId())){
            message.setSenderDeleted();
        }else if(message.getReceiver().getId().equals(user.getId())){
            message.setReceiverDeleted();
        }else{
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        if(message.hasToBeDeleted()) messageRepository.delete(message);
    }


}
