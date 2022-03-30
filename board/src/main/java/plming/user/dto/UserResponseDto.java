package plming.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

// User엔터티에서 image를 실제 프로필 사진으로 치환한 객체
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private Long id;
    private String nickname;
    private String password;
    private String image;
    private String email;
    private String introduce;
    private String github;
    private int social;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .image(image)
                .introduce(introduce)
                .social(social)
                .github(github).build();
    }

    public UserResponseDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.introduce = user.getIntroduce();
        this.github = user.getGithub();
        this.social = user.getSocial();
    }
}