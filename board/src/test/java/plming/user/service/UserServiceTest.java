package plming.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.tag.entity.TagRepository;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserJoinResponseDto;
import plming.user.dto.UserResponseDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void beforeEach(){
        userRepository.deleteAll();
    }

    @Test
    void getUserByNickName() {
        //given
        String nickname = "test_nickname";
        String password = "password";
        String email = "email@gamil.com";
        int social = 0;
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
        Long id = userJoinResponseDto.getId();

        //when
        UserResponseDto userResponseDto = userService.getUserByNickName(nickname);

        //then
        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());

        //after test
        userRepository.deleteById(id);
    }

    @Test
    void getUserById() {
        //given
        String nickname = "test_nickname";
        String password = "password";
        String email = "email@gamil.com";
        int social = 0;
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
        Long id = userJoinResponseDto.getId();

        //when
        UserResponseDto userResponseDto = userService.getUserByUserId(id);

        //then
        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());

        //after test
        userRepository.deleteById(id);
    }

    @Test
    void getUserByEmail() {
        //given
        String nickname = "test_nickname";
        String password = "password";
        String email = "email@gamil.com";
        int social = 0;
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
        UserJoinResponseDto userJoinResponseDto = userService.createUser(userJoinRequestDto);
        Long id = userJoinResponseDto.getId();

        //when
        UserResponseDto userResponseDto = userService.getUserByUserEmail(email);

        //then
        assertThat(userJoinResponseDto.getNickname()).isEqualTo(userResponseDto.getNickname());
        assertThat(userJoinResponseDto.getId()).isEqualTo(userResponseDto.getId());

        //after test
        userRepository.deleteById(id);
    }

    @Test
    void updateUser() {
        //given
        User user = User.builder().email("test@google.com")
                .nickname("testNickname")
                .role("ROLE_USER")
                .password("testPassword")
                .social(0).build();
        userRepository.save(user);

        UserUpdateRequestDto userUpdateRequestDto =
                new UserUpdateRequestDto(user.getId(),"updateNickname","update.jpg","","", List.of(0L,1L,2L));

        //when
        UserResponseDto userResponseDto = userService.updateUser(userUpdateRequestDto);
        List<String> tagsList = userResponseDto.getTagsList();

        //then
        for(int i = 0 ; i < tagsList.size();i++){
            assertThat(tagsList.get(i).equals(tagRepository.findById((long) i)));
        }
    }
}