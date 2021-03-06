package plming.user.controller;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plming.auth.service.JwtTokenProvider;
import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // C
    @PostMapping
    public ResponseEntity joinUser(@RequestBody UserJoinRequestDto userJoinRequestDto){
        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
        if(userJoinResponseDto != null){
            return ResponseEntity.status(201).body(userJoinResponseDto);
        }
        return ResponseEntity.status(400).build();
    }

    // R
    @GetMapping("/nickname/{nickName}")
    public UserResponseDto getUserByNickName(@NotNull @PathVariable String nickName){
        return userService.getUserByNickName(nickName);
    }

    // R
    @GetMapping("/id/{userId}")
    public UserResponseDto getUserById(@NotNull @PathVariable String userId){
        return userService.getUserByUserId(Long.parseLong(userId));
    }

    // R
    @GetMapping("/email/{email}")
    public UserResponseDto getUserByEmail(@NotNull @PathVariable String email){
        return userService.getUserByUserEmail(email);
    }

    // U
    @PatchMapping("/{userId}")
    public UserResponseDto updateUser(@NotNull @PathVariable Long userId, @RequestBody UserUpdateRequestDto userUpdateDto, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        userUpdateDto.setId(userId);
        return userService.updateUser(userUpdateDto);
    }

    /*
     * userId??? ????????? ?????? id??? ???????????? ?????? ??? 403 ????????? ????????????.
     * ?????? ????????? 403?????? ??????????????? ??????.
     * ??????, ????????? ??????????????? ??????????????????, DB?????? User??? ???????????? ????????????,
     * ????????? ????????? ????????? ?????? ?????????.
     */
    // D
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@NotNull @PathVariable Long userId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // ???????????? ??????
    @PostMapping("/{userId}/password-check")
    public ResponseEntity checkPassword(@NotNull @PathVariable Long userId,@RequestBody Map<String,String> requestBody, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        String password = requestBody.get("password");
        userService.checkPassword(userId,password);
        return ResponseEntity.ok().build();
    }

    // ???????????? ??????
    @PatchMapping("/{userId}/password")
    public ResponseEntity updatePassword(@NotNull @PathVariable Long userId, @RequestBody Map<String,String> requestBody, HttpServletRequest request, HttpSession session){
        String password = requestBody.get("password");
        try {
            jwtTokenProvider.validateTokenAndUserId(request,userId);
            userService.updatePassword(userId,password);
        }catch (Exception e){
            userService.updatePasswordBySession(userId,password,session);
        }
        return ResponseEntity.ok().build();
    }

}