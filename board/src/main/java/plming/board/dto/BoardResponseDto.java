package plming.board.dto;

import lombok.Getter;
import plming.board.entity.Board;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {

    private Long id;
    private String user;
    private String category;
    private String status;
    private String period;
    private String title;
    private String content;
    private Integer participantNum;
    private Long viewCnt;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private char deleteYn;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.user = entity.getUser();
        this.category = entity.getCategory();
        this.status = entity.getStatus();
        this.period = entity.getPeriod();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.participantNum = entity.getParticipantNum();
        this.viewCnt = entity.getViewCnt();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
        this.deleteYn = entity.getDeleteYn();
    }
}
