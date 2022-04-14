package plming.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserUpdateRequestDto {
    private Long id;
    private String nickname;
    private String image;
    private String introduce;
    private String github;
    private List<Long> tagIds;

    public void setId(Long id) {
        this.id = id;
    }
}