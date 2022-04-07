package plming.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String nickname;
    private String image;
    private String introduce;
    private String github;


    public void setId(Long id) {
        this.id = id;
    }
}