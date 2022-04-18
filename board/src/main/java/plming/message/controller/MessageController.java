package plming.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.message.dto.MessageListResponseDto;
import plming.message.dto.MessageRequestDto;
import plming.message.dto.MessageResponseDto;
import plming.message.service.MessageService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/{userId}")
    public Map<String, List<MessageListResponseDto>> getMessageAllList(@PathVariable Long userId){
        Map<String, List<MessageListResponseDto>> response = new HashMap<>();
        List<MessageListResponseDto> messageList = messageService.getMessageAllList(userId);
        response.put("messages",messageList);
        return response;
    }

    @GetMapping
    public Map<String, List<MessageResponseDto>> getMessageList(@RequestParam Long userId, @RequestParam Long otherId){
        if(userId.equals(otherId)) throw new CustomException(ErrorCode.BAD_REQUEST);
        Map<String, List<MessageResponseDto>> response = new HashMap<>();
        List<MessageResponseDto> messageList = messageService.getMessageList(userId,otherId);
        response.put("messages",messageList);
        return response;
    }

    @PostMapping
    public ResponseEntity postMessage(@RequestBody MessageRequestDto messageRequestDto){
        return ResponseEntity.status(201).body(messageService.postMessage(messageRequestDto));
    }

    @DeleteMapping("/all")
    public void deleteAllMessage(@RequestParam Long userId, @RequestParam Long otherId){
        if(userId.equals(otherId)) throw new CustomException(ErrorCode.BAD_REQUEST);
        messageService.deleteAllMessage(userId,otherId);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@RequestParam Long userId, @PathVariable Long messageId){
        messageService.deleteMessage(userId, messageId);
    }
}
