package plming.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.message.dto.MessageGroupResponseDto;
import plming.message.dto.MessageRequestDto;
import plming.message.dto.MessageDetailResponseDto;
import plming.message.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/user/{userId}")
    public List<MessageGroupResponseDto> getMessageGroupList(@PathVariable Long userId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        return messageService.getMessageGroupList(userId);
    }

    @GetMapping
    public List<MessageDetailResponseDto> getMessageDetailList(@RequestParam Long userId, @RequestParam Long otherId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        if(userId.equals(otherId)) throw new CustomException(ErrorCode.BAD_REQUEST);
        return messageService.getMessageDetailList(userId,otherId);
    }

    @PostMapping
    public ResponseEntity postMessage(@RequestBody MessageRequestDto messageRequestDto, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,messageRequestDto.getUserId());
        return ResponseEntity.status(201).body(messageService.postMessage(messageRequestDto));
    }

    @DeleteMapping("/all")
    public void deleteAllMessage(@RequestParam Long userId, @RequestParam Long otherId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        if(userId.equals(otherId)) throw new CustomException(ErrorCode.BAD_REQUEST);
        messageService.deleteAllMessage(userId,otherId);
    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(@RequestParam Long userId, @PathVariable Long messageId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        messageService.deleteMessage(userId, messageId);
    }
}
