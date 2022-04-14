//package plming.user.service;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.user.dto.UserJoinRequestDto;
//import plming.user.dto.UserJoinResponseDto;
//import plming.user.dto.UserResponseDto;
//import plming.user.entity.UserRepository;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//public class UserServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Test
//    void getUserByNickName() {
//        //given
//        String nickname = "test_nickname";
//        String password = "password";
//        String email = "email@gamil.com";
//        int social = 0;
//        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
//        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
//        Long id = userJoinResponseDto.getId();
//
//        //when
//        UserResponseDto userResponseDto = userService.getUserByNickName(nickname);
//
//        //then
//        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
//        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());
//
//        //after test
//        userRepository.deleteById(id);
//    }
//
//    @Test
//    void getUserById() {
//        //given
//        String nickname = "test_nickname";
//        String password = "password";
//        String email = "email@gamil.com";
//        int social = 0;
//        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
//        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
//        Long id = userJoinResponseDto.getId();
//
//        //when
//        UserResponseDto userResponseDto = userService.getUserByUserId(id);
//
//        //then
//        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
//        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());
//
//        //after test
//        userRepository.deleteById(id);
//    }
//
//    @Test
//    void getUserByEmail() {
//        //given
//        String nickname = "test_nickname";
//        String password = "password";
//        String email = "email@gamil.com";
//        int social = 0;
//        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
//        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
//        Long id = userJoinResponseDto.getId();
//
//        //when
//        UserResponseDto userResponseDto = userService.getUserByUserEmail(email);
//
//        //then
//        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
//        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());
//
//        //after test
//        userRepository.deleteById(id);
//    }
//
//}