package plming.message.controller;

import org.springframework.web.bind.annotation.*;
import plming.message.dto.MessageListResponseDto;
import plming.message.dto.MessageRequestDto;
import plming.message.dto.MessageResponseDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @GetMapping("/{userId}")
    public Map<String, List<MessageListResponseDto>> getMessageAllList(@PathVariable Long userId){
//        Map<String, List<MessageListResponseDto>> response = new HashMap<>();
//        List<MessageListResponseDto> messageList = ;
//        response.put("messages",messageList);
        return null;
    }

    @GetMapping
    public Map<String, List<MessageResponseDto>> getMessageList(
            @RequestParam("me") Long me, @RequestParam("other") Long other){
        return null;
    }

    @PostMapping
    public void postMessage(@RequestBody MessageRequestDto messageRequestDto){

    }

    @DeleteMapping("/all")
    public void deleteAllMessage(
            @RequestParam("me") Long me, @RequestParam("other") Long other
    ){

    }

    @DeleteMapping("/{messageId}")
    public void deleteMessage(
            @RequestParam("me") Long userId, @PathVariable Long messageId
    ){

    }
}
