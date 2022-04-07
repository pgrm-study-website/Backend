package plming.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.user.dto.UserJoinRequestDto;
import plming.user.dto.UserUpdateRequestDto;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @AfterEach
    private void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void createUserTest(){
        String nickname = "nickname";
        String password = "password";
        String email = "email@gamil.com";
        int social = 0;
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(nickname,password,email,social);
        userService.createUser(userJoinRequestDto);

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        assertThat(user.getNickname()).isEqualTo(nickname);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getSocial()).isEqualTo(social);
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
    }

//    @Test
//    public void updateUserTest(){
//        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto("nickname","password","email@gamil.com",0);
//        userService.createUser(userJoinRequestDto);
//
//        List<User> userList = userRepository.findAll();
//
//        Long id = userList.get(0).getId();
//        String nickname = "newNickName";
//        String image = "image.jpg";
//        String introduce = "test introduce";
//        String github = "githubID";
//
//        UserUpdateRequestDto userUpdateRequestDto = new UserUpdateRequestDto(id,nickname,image,introduce,github);
//        userService.updateUser(userUpdateRequestDto);
//
//        List<User> updateUserList = userRepository.findAll();
//        User user = updateUserList.get(0);
//
//        assertThat(user.getNickname()).isEqualTo(nickname);
//        assertThat(user.getImage()).isEqualTo(image);
//        assertThat(user.getGithub()).isEqualTo(github);
//        assertThat(user.getIntroduce()).isEqualTo(introduce);
//    }
}
