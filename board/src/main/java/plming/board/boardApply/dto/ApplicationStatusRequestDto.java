package plming.board.boardApply.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ApplicationStatusRequestDto {

    private final String status;
    private final String nickname;

    @Builder
    public ApplicationStatusRequestDto(String status, String nickname) {
        this.status = status;
        this.nickname = nickname;
    }
}