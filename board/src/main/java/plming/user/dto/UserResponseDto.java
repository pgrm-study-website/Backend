package plming.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

// User엔터티에서 image를 실제 프로필 사진으로 치환한 객체
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String nickname;
    private String image;
    private String email;
    private String introduce;
    private String github;

    public UserResponseDto(User user){
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.introduce = user.getIntroduce();
        this.github = user.getGithub();
    }
}