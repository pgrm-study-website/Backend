package plming.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinResponseDto {
    private Long id;
    private String nickname;
    private String image;
    private int social;

    public UserJoinResponseDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.image = user.getImage();
        this.social = user.getSocial();
    }
}