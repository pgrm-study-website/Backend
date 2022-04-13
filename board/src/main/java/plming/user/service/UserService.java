package plming.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.auth.service.JwtTokenProvider;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.user.dto.*;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto) {
        userJoinRequestDto.setPassword(bCryptPasswordEncoder.encode(userJoinRequestDto.getPassword()));
        User user = userJoinRequestDto.toEntity();

        if(isEmailOverlap(user.getEmail())) throw new CustomException(ErrorCode.EMAIL_OVERLAP);
        if(isNickNameOverlap(user.getNickname())) throw new CustomException(ErrorCode.NICKNAME_OVERLAP);

        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    public UserResponseDto getUserByNickName(String nickName) {
        User user = userRepository.findByNickname(nickName)
<<<<<<< HEAD
<<<<<<< HEAD
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
=======
          .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 5fbd76f... Fix: ErrorCode에 충돌난 부분 수정
=======
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        return new UserResponseDto(user);
    }

    public UserResponseDto getUserByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
<<<<<<< HEAD
<<<<<<< HEAD
        return new UserResponseDto(user);
    }

    public UserResponseDto getUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
=======
>>>>>>> 5fbd76f... Fix: ErrorCode에 충돌난 부분 수정
=======
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        return new UserResponseDto(user);
    }

    public UserListResponseDto getUserList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONTENT));
        return new UserListResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateDto, HttpServletRequest request) {
//        jwtTokenProvider.validateTokenAndUserId(request,userUpdateDto.getId());
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.INTERNAL_SERVER_ERROR));
        if(!user.getNickname().equals(userUpdateDto.getNickname()) && isNickNameOverlap(userUpdateDto.getNickname())){
            throw new CustomException(ErrorCode.NICKNAME_OVERLAP);
        }
        user.update(userUpdateDto);
        return new UserResponseDto(user);
    }

    @Transactional
<<<<<<< HEAD
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(user.getDeleteYn() == '1'){
            throw new CustomException(ErrorCode.ALREADY_DELETE);
        }
=======
    public void deleteUser(HttpServletRequest request,Long userId) {
<<<<<<< HEAD
<<<<<<< HEAD
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
>>>>>>> ce79537... Fix: 닉네임 중복 오류 및 사용자 삭제 deleteYn 추가
=======
=======
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(user.getDeleteYn() == '1'){
            throw new CustomException(ErrorCode.ALREADY_DELETE);
        }
<<<<<<< HEAD
>>>>>>> 5fbd76f... Fix: ErrorCode에 충돌난 부분 수정
=======
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        user.delete();
    }

    public void checkPassword(Long userId, String password) {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
=======
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
>>>>>>> ce79537... Fix: 닉네임 중복 오류 및 사용자 삭제 deleteYn 추가
=======
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 5fbd76f... Fix: ErrorCode에 충돌난 부분 수정
=======
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
    }

    @Transactional
    public void updatePassword(Long userId,String password) {
        User updateUser = userRepository.findById(userId)
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
                .orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
=======
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND));
>>>>>>> ce79537... Fix: 닉네임 중복 오류 및 사용자 삭제 deleteYn 추가
=======
                .orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 5fbd76f... Fix: ErrorCode에 충돌난 부분 수정
=======
                .orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
>>>>>>> 67d88c8... Fix: ErrorCode에 충돌난 부분 수정
        updateUser.updatePassword(bCryptPasswordEncoder.encode(password));
    }

    public boolean isEmailOverlap(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNickNameOverlap(String nickName){
        return userRepository.existsByNickname(nickName);
    }
}