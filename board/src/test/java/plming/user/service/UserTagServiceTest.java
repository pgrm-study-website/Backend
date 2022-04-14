package plming.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.entity.UserTagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTagServiceTest {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserTagService userTagService;

    private User user1;
    private User user2;
    private final Long[] user1TagIds = {10L, 20L};
    private final Long[] user2TagIds = {40L, 50L, 60L};

    @BeforeEach
    void beforeEach(){
        user1 = User.builder().email("test@google.com")
                .nickname("testNickname")
                .role("ROLE_USER")
                .password("testPassword")
                .social(0).build();
        user2 = User.builder().email("test2@google.com")
                .nickname("testNickname2")
                .role("ROLE_USER")
                .password("testPassword2")
                .social(0).build();
        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    void save() {
        userTagService.save(List.of(user1TagIds),user1);
        userTagService.save(List.of(user2TagIds),user2);
    }

    @Test
    void findTagNameByUser() {
        //when
        userTagService.save(List.of(user1TagIds),user1);
        userTagService.save(List.of(user2TagIds),user2);
        List<String> userTagNameList1 = userTagService.findTagNameByUser(user1);
        List<String> userTagNameList2 = userTagService.findTagNameByUser(user2);


        //then
        assertThat(userTagNameList1.size()).isEqualTo(user1TagIds.length);
        assertThat(userTagNameList2.size()).isEqualTo(user2TagIds.length);
    }
}
