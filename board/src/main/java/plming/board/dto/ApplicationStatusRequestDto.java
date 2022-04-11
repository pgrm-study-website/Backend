package plming.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationStatusRequestDto {

    private String status;
    private String nickname;

    @Builder
    public ApplicationStatusRequestDto(String status, String nickname) {
        this.status = status;
        this.nickname = nickname;
    }
}
