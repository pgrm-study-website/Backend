package plming.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserListResponseDto {

    private Long id;
    private String nickname;
    private String image;
    private String email;
    private String introduce;
    private String github;

    public UserListResponseDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.introduce = user.getIntroduce();
        this.github = user.getGithub();
    }
}