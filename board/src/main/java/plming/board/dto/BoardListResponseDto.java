package plming.board.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListResponseDto {

    private Long id;
    private String category;
    private String status;
    private String title;
    private Integer participantMax;
    private Long viewCnt;
    private List<String> tags;
    private Integer participantNum;

    public BoardListResponseDto(Board entity, Integer participantNum, List<String> tags) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.status = entity.getStatus();
        this.title = entity.getTitle();
        this.participantMax = entity.getParticipantMax();
        this.participantNum = participantNum;
        this.viewCnt = entity.getViewCnt();
        this.tags = tags;
    }
}
