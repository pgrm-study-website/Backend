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
     * userId와 토큰에 담긴 id가 동일하지 않을 때 403 코드를 반환한다.
     * 모든 에러는 403으로 통일되어야 한다.
     * 만약, 인증이 정상적으로 통과되었는데, DB에서 User가 조회되지 않는다면,
     * 서버의 로직에 문제가 있는 것이다.
     */
    // D
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@NotNull @PathVariable Long userId, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    // 비밀번호 확인
    @PostMapping("/{userId}/password-check")
    public ResponseEntity checkPassword(@NotNull @PathVariable Long userId,@RequestBody Map<String,String> requestBody, HttpServletRequest request){
        jwtTokenProvider.validateTokenAndUserId(request,userId);
        String password = requestBody.get("password");
        userService.checkPassword(userId,password);
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
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