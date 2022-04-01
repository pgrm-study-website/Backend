package plming.user.dto;

import lombok.*;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequestDto {
    private String nickname;
    private String password;
    private String email;
    private int social;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .role("ROLE_USER")
                .social(social).build();
    }
}