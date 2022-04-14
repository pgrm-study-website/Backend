package plming.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    // PK

    /* 추가 */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "enum")
    private String category;    // 카테고리

    @Column(columnDefinition = "enum")
    private String status;  // 모집 상태

    @Column(columnDefinition = "integer")
    private Integer period;  // 진행 기간

    @Column(columnDefinition = "varchar")
    private String title;   // 제목

    @Column(columnDefinition = "varchar")
    private String content; // 내용

    @Column(columnDefinition = "Integer")
    private Integer participantMax = 0;

    @Column(columnDefinition = "bigint")
    private Long viewCnt = 0L;

    @Column(columnDefinition = "datetime")
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(columnDefinition = "datetime")
    private LocalDateTime updateDate;

    @Column(columnDefinition = "enum")
    private char deleteYn = '0';

    // 역방향
    @OneToMany(mappedBy = "board")
    private List<BoardTag> boardTags;

    @Builder
    public Board(User user, Integer participantMax, String category, String status, Integer period, String title, String content, List<BoardTag> boardTags) {
        this.user = user;
        this.participantMax = participantMax;
        this.category = category;
        this.status = status;
        this.period = period;
        this.title = title;
        this.content = content;
        this.boardTags = boardTags;
    }

    /**
     * 게시글 수정
     */
    public void update(Integer participantMax, String title, String content, String category, String status, Integer period) {
        this.participantMax = participantMax;
        this.title = title;
        this.content = content;
        this.category = category;
        this.status = status;
        this.period = period;
        this.updateDate = LocalDateTime.now();
    }

    /**
     * 게시글 모집 상태 업데이트
     */
    public void updateStatus(String status) {
        this.status = status;
    }

    /**
     * 게시글 조회 수 증가
     */
    public void increaseCount() {
        this.viewCnt++;
    }

    /**
     * 게시글 삭제
     */
    public void delete() {
        this.deleteYn = '1';
    }

}