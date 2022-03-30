package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plming.user.dto.UserResponseDto;
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
    public boolean createUser(UserJoinRequestDto userJoinRequestDto) {
        User user = userJoinRequestDto.toEntity();
        return userRepository.save(user) != null;
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        try{
            // 결과가 null일 경우 get()에서 exception을 throw
            User user = userRepository.findById(userId).get();
            return new UserResponseDto(user);
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
}
