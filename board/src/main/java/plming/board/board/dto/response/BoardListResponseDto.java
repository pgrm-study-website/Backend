package plming.board.board.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.board.entity.Board;

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

    public BoardListResponseDto(Board entity, Integer participantNum, List<String> tagNames) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.status = entity.getStatus();
        this.title = entity.getTitle();
        this.participantMax = entity.getParticipantMax();
        this.participantNum = participantNum;
        this.viewCnt = entity.getViewCnt();
        this.tags = tagNames;
    }
}