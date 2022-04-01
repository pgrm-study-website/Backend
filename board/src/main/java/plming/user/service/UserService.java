package plming.user.service;


import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserReadResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;

public interface UserService {

    UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto);
    UserReadResponseDto getUser(Long userId);
    boolean updateUser(UserUpdateRequestDto userUpdateDto);
    boolean deleteUser(Long userId);
    boolean checkPassword(Long userId,String password);
    boolean updatePassword(Long userId,String password);
    boolean isEmailOverlap(String email);
}
