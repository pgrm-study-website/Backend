package plming.user.service;


import plming.user.dto.UserResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;

public interface UserService {

    boolean createUser(UserJoinRequestDto userJoinRequestDto);
    UserResponseDto getUser(Long userId);
    boolean updateUser(UserUpdateRequestDto userUpdateDto);
    boolean deleteUser(Long userId);
}
