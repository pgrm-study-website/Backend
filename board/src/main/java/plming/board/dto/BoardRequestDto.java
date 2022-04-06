package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.entity.Board;
import plming.user.entity.User;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDto {

    private Long userId;    // 사용자
    private Integer participantMax; // 최대 참여 인원 수
    private String category;    // 카테고리
    private String status;  // 모집 상태
    private Integer period;  // 진행 기간
    private String title;   // 제목
    private String content; // 내용
    private List<Long> tagIds;  // tag ID 리스트

    public Board toEntity(User user) {
        return Board.builder()
                .user(user)
                .participantMax(participantMax)
                .title(title)
                .status(status)
                .category(category)
                .period(period)
                .content(content)
                .build();
    }
}
