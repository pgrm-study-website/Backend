package plming.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSocialJoinRequestDto {
    private String nickname;
    private int social;
    private String socialId;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .social(social)
                .socialId(socialId)
                .role("ROLE_USER")
                .build();
    }
}
