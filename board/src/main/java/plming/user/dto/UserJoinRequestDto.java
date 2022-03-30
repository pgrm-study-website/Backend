package plming.user.dto;

import lombok.*;
import plming.user.entity.User;

// User엔터티에서 image를 실제 프로필 사진으로 치환한 객체
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