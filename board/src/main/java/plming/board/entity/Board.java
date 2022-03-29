package plming.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK

    @Column(columnDefinition = "varchar")
    private String user;    // 사용자

    @Column(columnDefinition = "enum")
    private String category;    // 카테고리

    @Column(columnDefinition = "enum")
    private String status;  // 모집 상태

    @Column(columnDefinition = "varchar")
    private String period;  // 진행 기간

    @Column(columnDefinition = "varchar")
    private String title;   // 제목

    @Column(columnDefinition = "varchar")
    private String content; // 내용

    @Column(columnDefinition = "Integer")
    private Integer participantNum = 0;

    @Column(columnDefinition = "bigint")
    private Long viewCnt = 0L;

    @Column(columnDefinition = "datetime")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(columnDefinition = "datetime")
    private LocalDateTime updateDate;

    @Column(columnDefinition = "enum")
    private char deleteYn = 'N';

    @Builder
    public Board(String user, String category, String status, String period, String title, String content) {
        this.user = user;
        this.category = category;
        this.status = status;
        this.period = period;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content, String category, String status, String period) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.status = status;
        this.period = period;
        this.updateDate = LocalDateTime.now();
    }
}
