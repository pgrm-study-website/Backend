package plming.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoardListResponseDto {

    Page<BoardListResponseDto> write;
    Page<BoardListResponseDto> comment;

    @Builder
    public UserBoardListResponseDto(Page<BoardListResponseDto> write, Page<BoardListResponseDto> comment) {
        this.write = write;
        this.comment = comment;
    }
}
