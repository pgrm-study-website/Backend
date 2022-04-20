package plming.board.board.dto.response;

import lombok.Getter;
import plming.board.board.entity.Board;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BoardResponseDto {

    private final Long id;
    private final String category;
    private final String status;
    private final Integer period;
    private final String title;
    private final String content;
    private final Long userId;
    private final Integer participantMax;
    private final Integer participantNum;
    private final Long viewCnt;
    private final LocalDateTime createDate;
    private final LocalDateTime updateDate;
    private final char deleteYn;
    private final List<String> tags;

    public BoardResponseDto(Board entity, Integer participantNum, List<String> tags) {
        this.id = entity.getId();
        this.category = entity.getCategory();
        this.status = entity.getStatus();
        this.period = entity.getPeriod();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUser().getId();
        this.participantNum = participantNum;
        this.participantMax = entity.getParticipantMax();
        this.viewCnt = entity.getViewCnt();
        this.createDate = entity.getCreateDate();
        this.updateDate = entity.getUpdateDate();
        this.deleteYn = entity.getDeleteYn();
        this.tags = tags;
    }
}