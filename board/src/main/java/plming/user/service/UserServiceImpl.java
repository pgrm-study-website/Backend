package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserReadResponseDto;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto) {
        User user = userJoinRequestDto.toEntity();
        if(isEmailOverlap(user.getEmail())){
            return null;
        }
        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    @Override
    public UserReadResponseDto getUser(Long userId) {
        try{
            // 결과가 null일 경우 get()에서 exception을 throw
            User user = userRepository.findById(userId).get();
            return new UserReadResponseDto(user);
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updateUser(UserUpdateRequestDto userUpdateDto) {
        try {
            User updateUser = userRepository.findById(userUpdateDto.getId()).get();
            updateUser.update(userUpdateDto);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long userId) {
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean checkPassword(Long userId, String password) {
        return userRepository.existsByIdAndPassword(userId,password);
    }

    @Override
    @Transactional
    public boolean updatePassword(Long userId,String password) {
        try {
            User updateUser = userRepository.findById(userId).get();
            updateUser.updatePassword(password);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    @Override
    public boolean isEmailOverlap(String email) {
        return userRepository.existsByEmail(email);
    }
}
