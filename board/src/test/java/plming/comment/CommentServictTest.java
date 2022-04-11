package plming.comment;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.comment.entity.Comment;
import plming.comment.entity.CommentRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommentServictTest {

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
    private Comment comment1;
    private Comment comment2;
    private Comment comment3;

    @BeforeEach
    void beforeEach() {
        user1 = User.builder()
                .nickname("nickname1")
                .email("email1@gmail.com")
                .role("ROLE_USER")
                .social(0)
                .build();
        user2 = User.builder()
                .nickname("nickname1")
                .email("email@gmail1.com")
                .role("ROLE_USER")
                .social(0)
                .build();

        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
                .participantMax(5)
                .build();
        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
                .period(1).category("프로젝트").status("모집 중").title("사용자2의 게시글 1")
                .participantMax(3)
                .build();

        comment1 = Comment.builder()
                .content("user2가 남기는 post1의 첫 번째 댓글입니다.")
                .parentId(null).board(post1).user(user2)
                .build();
        comment2 = Comment.builder()
                .content("user1이 남기는 post2의 첫 번째 댓글입니다.")
                .parentId(null).board(post2).user(user1)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        boardRepository.save(post1);
        boardRepository.save(post2);
    }

    @AfterEach
    void afterEach() {
//        commentRepository.deleteAll();
//        boardRepository.deleteAll();
//        userRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성")
    @Transactional
    void save() {

        // when
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        comment3 = Comment.builder()
                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
                .parentId(comment1.getId()).board(post1).user(user2)
                .build();
        commentRepository.save(comment3);

        // then
        assertEquals(comment1.getId(), commentRepository.getById(comment3.getId()).getParentId());
    }

    @Test
    @DisplayName("게시글 댓글 조회")
    void findCommentByBoardId() {

        // given
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        comment3 = Comment.builder()
                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
                .parentId(comment1.getId()).board(post1).user(user2)
                .build();
        commentRepository.save(comment3);

        // when
        List<Comment> post1CommentList = commentRepository.findCommentByBoardId(post1.getId());
        List<Comment> post2CommentList = commentRepository.findCommentByBoardId(post2.getId());

        // then
        assertEquals(1, post1CommentList.size());
        assertEquals(comment1.getId(), post1CommentList.get(0).getId());
        assertEquals(1, post2CommentList.size());
    }

    @Test
    @DisplayName("게시글 대댓글 조회")
    void findRecommentByBoardId() {

        // given
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        comment3 = Comment.builder()
                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
                .parentId(comment1.getId()).board(post1).user(user2)
                .build();
        commentRepository.save(comment3);

        // when
        List<Comment> recommentList = commentRepository.findRecommentByCommentId(comment1.getId());

        // then
        assertEquals(1, recommentList.size());
        assertEquals(comment1.getId(), recommentList.get(0).getParentId());
    }
}
