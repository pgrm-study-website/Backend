package plming.comment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.entity.Board;
import plming.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar")
    private String content;

    @Column(columnDefinition = "bigint")
    private Long parentId;

    @Column(columnDefinition = "enum")
    private char deleteYn = '0';

    @Column(columnDefinition = "datetime")
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comment(String content, Long parentId, Board board, User user) {
        this.content = content;
        this.parentId = parentId;
        this.board = board;
        this.user = user;
    }
}
