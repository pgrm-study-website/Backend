package plming.user.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserReadResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.service.UserService;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // C
    @PostMapping("/")
    public ResponseEntity joinUser(@RequestBody UserJoinRequestDto userJoinRequestDto){
        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
        if(userJoinResponseDto != null){
            return ResponseEntity.status(201).body(userJoinResponseDto);
        }
        return ResponseEntity.status(400).build();
    }

    // R
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@NotNull @PathVariable(value = "userId") Long userId){
        UserReadResponseDto userDto = userService.getUser(userId);
        if(userDto != null){
            return ResponseEntity.status(200).body(userDto);
        }
        return ResponseEntity.status(204).build();
    }

    // U
    @PatchMapping("/{userId}")
    public ResponseEntity updateUser(@NotNull @PathVariable(value = "userId") Long userId,@RequestBody UserUpdateRequestDto userUpdateDto){
        userUpdateDto.setId(userId);
        if(userService.updateUser(userUpdateDto)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(204).build();
    }

    // D
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@NotNull @PathVariable(value = "userId") Long userId){
        if(userService.deleteUser(userId)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(204).build();
    }

    // 비밀번호 확인
    @PostMapping("/{userId}/password-check")
    public ResponseEntity checkPassword(@PathVariable(value = "userId") Long userId,@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        if(userService.checkPassword(userId,password)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(400).build();

    }

    // 비밀번호 변경
    @PatchMapping("/{userId}/password")
    public ResponseEntity updatePassword(@PathVariable(value = "userId") Long userId,@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        if(userService.updatePassword(userId,password)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(500).build();
    }

}