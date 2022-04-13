package plming.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoardListResponseDto {

    List<BoardListResponseDto> write;
    List<BoardListResponseDto> comment;

    @Builder
    public UserBoardListResponseDto(List<BoardListResponseDto> write, List<BoardListResponseDto> comment) {
        this.write = write;
        this.comment = comment;
    }
}
