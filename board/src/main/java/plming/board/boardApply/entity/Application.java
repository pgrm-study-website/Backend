package plming.board.boardApply.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plming.board.board.entity.Board;
import plming.user.entity.User;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Board board;

    @Column(columnDefinition = "enum")
    private String status = "대기";

    @Builder
    public Application(User user, Board board, String status) {
        this.user = user;
        this.board = board;
        this.status = status;
    }

}