package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.user.dto.*;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto) {
        User user = userJoinRequestDto.toEntity();
        if(isEmailOverlap(user.getEmail())){
            return null;
        }
        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONTENT));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        user.update(userUpdateDto);
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        if(!userRepository.existsById(userId)){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        userRepository.deleteById(userId);
    }

    public void checkPassword(Long userId, String password) {
        if(!userRepository.existsByIdAndPassword(userId,password)){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    @Transactional
    public void updatePassword(Long userId,String password) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        updateUser.updatePassword(password);
    }

    public boolean isEmailOverlap(String email) {
        return userRepository.existsByEmail(email);
    }
}
