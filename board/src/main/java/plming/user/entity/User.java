package plming.user.entity;

import lombok.*;
import org.springframework.util.Assert;
import plming.user.dto.UserUpdateRequestDto;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    @Column(name= "nickname", nullable = false)
    private String nickname; // 닉네임, 수정O

    @Column(name= "email", nullable = false)
    private String email; // 이메일, 수정X

    @Column(name= "password")
    private String password; // 비밀번호, 수정O

    @Column(name= "image")
    private String image; // 프로필사진파일명, 수정O

    @Column(name= "introduce")
    private String introduce; // 자기소개, 수정O

    @Column(name= "github")
    private String github; // 깃허브ID, 수정O

    @Column(name= "role", nullable = false)
    private String role; // ROLE_USER, ROLE_ADMIN, 수정X

    @Column(name= "social", nullable = false)
    private int social; // 회원가입방법(0 : 기본, 1 : 구글, 2 : 카카오, 3 : 깃허브), 수정X

    @Builder
    public User(Long id,String nickname, String email, String password, String image, String introduce, String github, String role, int social){
        Assert.hasText(nickname,"nickname must not be null");
        Assert.hasText(email,"email must not be null");
        Assert.hasText(role,"role must not be null");
        Assert.isTrue(social >= 0 && social <= 3,"social must be '0 <= social <= 2'");
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.image = image;
        this.introduce = introduce;
        this.github = github;
        this.role = role;
        this.social = social;
    }

    public void update(UserUpdateRequestDto userUpdateDto){
        this.nickname = userUpdateDto.getNickname();
        this.password = userUpdateDto.getPassword();
        this.image = userUpdateDto.getImage();
        this.introduce = userUpdateDto.getIntroduce();
        this.github = userUpdateDto.getGithub();
    }
}
