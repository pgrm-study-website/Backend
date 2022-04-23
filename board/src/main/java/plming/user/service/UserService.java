package plming.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import plming.auth.service.JwtTokenProvider;
import plming.board.boardApply.repository.ApplicationRepository;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.message.service.MessageService;
import plming.notification.repository.NotificationRepository;
import plming.storage.service.StorageService;
import plming.user.dto.*;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.entity.UserTagRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTagService userTagService;
    private final UserTagRepository userTagRepository;
    private final StorageService storageService;
    private final MessageService messageService;
    private final NotificationRepository notificationRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public UserJoinResponseDto createUser(UserJoinRequestDto userJoinRequestDto) {
        if(isEmailOverlap(userJoinRequestDto.getEmail())) throw new CustomException(ErrorCode.EMAIL_OVERLAP);
        if(isNickNameOverlap(userJoinRequestDto.getNickname())) throw new CustomException(ErrorCode.NICKNAME_OVERLAP);
        userJoinRequestDto.setPassword(bCryptPasswordEncoder.encode(userJoinRequestDto.getPassword()));
        User user = userJoinRequestDto.toEntity();
        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    @Transactional
    public UserJoinResponseDto createSocialUser(UserSocialJoinRequestDto userSocialJoinRequestDto){
        if(isNickNameOverlap(userSocialJoinRequestDto.getNickname())) throw new CustomException(ErrorCode.NICKNAME_OVERLAP);
        User user = userSocialJoinRequestDto.toEntity();
        userRepository.save(user);
        return new UserJoinResponseDto(user.getId(),user.getNickname(),user.getImage());
    }

    public UserResponseDto getUserByNickName(String nickName) {
        User user = userRepository.findByNickname(nickName)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return toUserResponseDto(user);
    }

    public UserResponseDto getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return toUserResponseDto(user);
    }

    public UserResponseDto getUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        return toUserResponseDto(user);
    }

    private UserResponseDto toUserResponseDto(User user) {
        List<String> tagsList = userTagService.findTagNameByUser(user);
        return new UserResponseDto(user, tagsList);
    }

    public UserListResponseDto getUserList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.NO_CONTENT));
        return new UserListResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto userUpdateDto) {
        User user = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(user.getDeleteYn() == '1'){
            throw new CustomException(ErrorCode.ALREADY_DELETE);
        }
        if(!user.getNickname().equals(userUpdateDto.getNickname()) && isNickNameOverlap(userUpdateDto.getNickname())){
            throw new CustomException(ErrorCode.NICKNAME_OVERLAP);
        }
        if(userUpdateDto.getTagIds().size() > 10){
            throw new CustomException(ErrorCode.USER_TAG_EXCESS);
        }
        if(userUpdateDto.getImage() != null && !storageService.isFileExist(userUpdateDto.getImage())){
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
        if(user.getImage() != null){
            if(userUpdateDto.getImage() == null || !user.getImage().equals(userUpdateDto.getImage())){
                storageService.deleteImage(user.getImage());
            }
        }

        userTagRepository.deleteAllByUser(user);
        userTagService.save(userUpdateDto.getTagIds(),user);
        user.update(userUpdateDto);
        return toUserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(user.getDeleteYn() == '1'){
            throw new CustomException(ErrorCode.ALREADY_DELETE);
        }
        if(user.getImage() != null){
            storageService.deleteImage(user.getImage());
        }
        notificationRepository.deleteAllByUserId(userId);
        applicationRepository.deleteAllByUser(user);
        messageService.deleteAllMessageByUserId(userId);
        userTagRepository.deleteAllByUser(user);
        user.delete();
    }

    public void checkPassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new CustomException(ErrorCode.LOGIN_UNAUTHORIZED_PASSWORD);
        }
    }

    @Transactional
    public void updatePassword(Long userId,String password) {
        User updateUser = userRepository.findById(userId)
                .orElseThrow(()->new CustomException(ErrorCode.USERS_NOT_FOUND));
        updateUser.updatePassword(bCryptPasswordEncoder.encode(password));
    }

    public boolean isEmailOverlap(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isNickNameOverlap(String nickName){
        return userRepository.existsByNickname(nickName);
    }
}
