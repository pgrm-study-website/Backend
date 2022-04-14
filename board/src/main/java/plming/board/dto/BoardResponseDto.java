package plming.board.dto;

import lombok.Getter;
import plming.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {

    private Long id;
    private String category;
    private String status;
    private Integer period;
    private String title;
    private String content;
    private Integer participantMax;
    private Integer participantNum;
    private Long viewCnt;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private char deleteYn;
    private List<String> tags;

    public BoardResponseDto(Board entity, Integer participantNum, List<String> tags) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.status = entity.getStatus();
        this.period = entity.getPeriod();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.participantNum = participantNum;
        this.participantMax = entity.getParticipantMax();
        this.viewCnt = entity.getViewCnt();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
        this.deleteYn = entity.getDeleteYn();
        this.tags = tags;
    }
}
