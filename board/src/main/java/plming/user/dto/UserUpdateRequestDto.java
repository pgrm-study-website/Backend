package plming.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String password;
    private String nickname;
    private String image;
    private String introduce;
    private String github;

    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .nickname(nickname)
                .image(image)
                .introduce(introduce)
                .github(github)
                .role("ROLE_USER")
                .build();
    }

    public void setId(Long id) {
        this.id = id;
    }
}
