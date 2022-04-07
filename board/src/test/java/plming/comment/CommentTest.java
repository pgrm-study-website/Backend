package plming.comment;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.comment.entity.Comment;
import plming.comment.entity.CommentRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Board post1;
    private Board post2;
    private User user1;
    private User user2;
    private Comment commentA;
    private Comment commentA_1;
    private Comment commentB;
    private Comment commentB_1;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder()
                .nickname("nickname1").email("email1@gmail.com")
                .role("ROLE_USER").social(0)
                .build();
        user2 = User.builder()
                .nickname("nickname1").email("email@gmail1.com")
                .role("ROLE_USER").social(0)
                .build();

        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
                .period("1개월").category("스터디").status("모집 중").title("사용자1의 게시글1")
                .participantMax(5)
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period("1개월").category("프로젝트").status("모집 중").title("사용자2의 게시글 1")
                .participantMax(3)
                .build();

        commentA = Comment.builder()
                .user(user1)
                .board(post1)
                .parentId(null)
                .content("post1의 첫 번째 댓글입니다.")
                .build();
        commentB = Comment.builder()
                .user(user1)
                .board(post1)
                .parentId(null)
                .content("post2의 첫 번째 댓글입니다.")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        boardRepository.save(post1);
        boardRepository.save(post2);
    }

    @AfterEach
    void afterEach() {
        commentRepository.deleteAll();
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 달기")
    void save() {
        // when
        commentRepository.save(commentA);
        commentRepository.save(commentB);

        commentA_1 = Comment.builder()
                .board(commentA.getBoard())
                .user(commentA.getUser())
                .parentId(commentA.getId())
                .content("post1의 첫 번째 댓글의 대댓글입니다.")
                .build();
        commentRepository.save(commentA_1);

        commentB_1 = Comment.builder()
                .board(commentB.getBoard())
                .user(commentB.getUser())
                .parentId(commentB.getId())
                .content("post2의 첫 번째 댓글의 대댓글입니다.")
                .build();
        commentRepository.save(commentB_1);

        // then
        List<Comment> commentList = commentRepository.findAll();

        assertEquals(commentList.get(commentList.size() - 2).getBoard().getId(), post1.getId());
        assertEquals(commentList.get(commentList.size() - 1).getParentId(), commentB.getId());
    }

}
