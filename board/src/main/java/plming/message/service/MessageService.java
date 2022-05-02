package plming.message.service;

import lombok.RequiredArgsConstructor;
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
import plming.user.entity.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public List<MessageGroupResponseDto> getMessageGroupList(Long userId){
        User user = getUserById(userId);
        List<Message> messageList = messageRepository.findMessageGroupByUser(user);
        return messageList.stream().map(m -> new MessageGroupResponseDto(m, userId)).collect(Collectors.toList());
    }

    public List<MessageDetailResponseDto> getMessageDetailList(Long userId, Long otherId){
        User user = getUserById(userId);
        User other = getUserById(otherId);
        List<Message> messageList = messageRepository.findMessageByUserAndOther(user,other);
        return messageList.stream().map(m -> new MessageDetailResponseDto(m,userId)).collect(Collectors.toList());
    }

    @Transactional
    public MessageDetailResponseDto postMessage(MessageRequestDto messageRequestDto){
        User user = getUserById(messageRequestDto.getUserId());
        User other = getUserById(messageRequestDto.getOtherId());
        Message message = messageRepository.save(messageRequestDto.toEntity(user,other));
        eventPublisher.publishEvent(new MessageCreateEvent(message, message.getReceiver(), NotificationType.message));
        return new MessageDetailResponseDto(message,messageRequestDto.getUserId());
    }

    public void deleteAllMessageByUserId(Long userId){
        User user = getUserById(userId);
        List<Message> messageList = messageRepository.findAllBySenderOrReceiver(user,user);
        messageList.forEach(m -> deleteMessage(userId, m.getId()));
    }

    @Transactional
    public void deleteAllMessage(Long userId, Long otherId){
        User user = getUserById(userId);
        User other = getUserById(otherId);

        List<Message> messageList = messageRepository.findMessageByUserAndOther(user,other);
        for(Message m : messageList){
            if(m.getSender().getId().equals(userId)){
                m.setSenderDeleted();
            }else{
                m.setReceiverDeleted();
            }
        }
        messageList.stream().filter(Message::hasToBeDeleted).forEach(messageRepository::delete);
    }

    @Transactional
    public void deleteMessage(Long userId, Long messageId){
        User user = getUserById(userId);
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

    private User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }
}
