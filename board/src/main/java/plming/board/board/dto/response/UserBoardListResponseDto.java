package plming.board.board.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.board.dto.response.BoardListResponseDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoardListResponseDto {

    List<BoardListResponseDto> write;
    List<BoardListResponseDto> comment;
    List<BoardListResponseDto> apply;

    @Builder
    public UserBoardListResponseDto(List<BoardListResponseDto> write, List<BoardListResponseDto> comment,
                                    List<BoardListResponseDto> apply)
    {
        this.write = write;
        this.comment = comment;
        this.apply = apply;
    }
}