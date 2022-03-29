package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.entity.Board;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private String user;    // 사용자
    private String category;    // 카테고리
    private String status;  // 모집 상태
    private String period;  // 진행 기간
    private String title;   // 제목
    private String content; // 내용

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .user(user)
                .status(status)
                .category(category)
                .period(period)
                .content(content)
                .build();
    }

}
