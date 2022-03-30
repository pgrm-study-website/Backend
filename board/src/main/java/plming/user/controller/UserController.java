package plming.user.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.user.dto.UserResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // C
    @PostMapping("/")
    public ResponseEntity joinUser(@RequestBody UserJoinRequestDto userJoinRequestDto){
        if(userService.createUser(userJoinRequestDto)){
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(500).build();
    }

    // R
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@NotNull @PathVariable(value = "userId") Long userId){
        UserResponseDto userDto = userService.getUser(userId);
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

    // 이메일 중복확인
    @GetMapping("/exist")
    public ResponseEntity confirmEmailOverlap(@RequestParam String email){
        System.out.println(email);
        return ResponseEntity.ok().body(userService.isEmailOverlap(email));
    }
}