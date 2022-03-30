package plming.user.entity;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void User_nickname_exception(){
        assertThrows(IllegalArgumentException.class,()-> User.builder()
                .email("test@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build());
    }

    @Test
    public void User_email_exception(){
        assertThrows(IllegalArgumentException.class,()-> User.builder()
                .nickname("nickname")
                .role("ROLE_USER")
                .social(0)
                .build());
    }

    @Test
    public void User_role_exception(){
        assertThrows(IllegalArgumentException.class,()-> User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .social(0)
                .build());
    }

    @Test
    public void User_social_exception(){
        assertThrows(IllegalArgumentException.class,()-> User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .role("ROLE_USER")
                .social(-1)
                .build());
    }

    @Test
    public void User_test(){
        User user = User.builder()
                .nickname("nickname")
                .email("email@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();

        assertThat(user.getNickname()).isEqualTo("nickname");
        assertThat(user.getEmail()).isEqualTo("email@gmail.com");
        assertThat(user.getRole()).isEqualTo("ROLE_USER");
        assertThat(user.getSocial()).isEqualTo(0);
    }
}
