//package plming.comment;
//
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.board.board.entity.Board;
//import plming.board.board.repository.BoardRepository;
//import plming.exception.CustomException;
//import plming.board.boardComment.entity.Comment;
//import plming.board.boardComment.entity.CommentRepository;
//import plming.board.boardComment.service.CommentService;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class CommentServictTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private CommentService commentService;
//
//    private Board post1;
//    private Board post2;
//    private User user1;
//    private User user2;
//    private Comment comment1;
//    private Comment comment2;
//    private Comment comment3;
//
//    @BeforeEach
//    void beforeEach() {
//        user1 = User.builder()
//                .nickname("nickname1")
//                .email("email1@gmail.com")
//                .role("ROLE_USER")
//                .social(0)
//                .build();
//        user2 = User.builder()
//                .nickname("nickname2")
//                .email("email@gmail1.com")
//                .role("ROLE_USER")
//                .social(0)
//                .build();
//
//        post1 = Board.builder().user(user1).content("사용자1의 첫 번째 게시글입니다.")
//                .period(1).category("스터디").status("모집 중").title("사용자1의 게시글1")
//                .participantMax(5)
//                .build();
//        post2 = Board.builder().user(user2).content("사용자2의 첫 번째 게시글입니다.")
//                .period(1).category("프로젝트").status("모집 중").title("사용자2의 게시글 1")
//                .participantMax(3)
//                .build();
//
//        comment1 = Comment.builder()
//                .content("user2가 남기는 post1의 첫 번째 댓글입니다.")
//                .parentId(null).board(post1).user(user2)
//                .build();
//        comment2 = Comment.builder()
//                .content("user1이 남기는 post2의 첫 번째 댓글입니다.")
//                .parentId(null).board(post2).user(user1)
//                .build();
//
//        userRepository.save(user1);
//        userRepository.save(user2);
//        boardRepository.save(post1);
//        boardRepository.save(post2);
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//    }
//
//    @AfterEach
//    void afterEach() {
//        commentRepository.deleteAll();
//        boardRepository.deleteAll();
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("댓글 생성")
//    void save() {
//
//        // when
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//        comment3 = Comment.builder()
//                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
//                .parentId(comment1.getId()).board(post1).user(user2)
//                .build();
//        commentRepository.save(comment3);
//
//        // then
//        assertEquals(comment1.getId(), commentRepository.findById(comment3.getId()).get().getParentId());
//    }
//
//    @Test
//    @DisplayName("게시글 댓글 조회")
//    void findCommentByBoardId() {
//
//        // given
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//        comment3 = Comment.builder()
//                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
//                .parentId(comment1.getId()).board(post1).user(user2)
//                .build();
//        commentRepository.save(comment3);
//
//        // when
//        List<Comment> post1CommentList = commentRepository.findCommentByBoardId(post1.getId());
//        List<Comment> post2CommentList = commentRepository.findCommentByBoardId(post2.getId());
//
//        // then
//        assertEquals(1, post1CommentList.size());
//        assertEquals(comment1.getId(), post1CommentList.get(0).getId());
//        assertEquals(1, post2CommentList.size());
//    }
//
//    @Test
//    @DisplayName("게시글 대댓글 조회")
//    void findRecommentByBoardId() {
//
//        // given
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//        comment3 = Comment.builder()
//                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
//                .parentId(comment1.getId()).board(post1).user(user2)
//                .build();
//        commentRepository.save(comment3);
//
//        // when
//        List<Comment> recommentList = commentRepository.findRecommentByCommentId(comment1.getId());
//
//        // then
//        assertEquals(1, recommentList.size());
//        assertEquals(comment1.getId(), recommentList.get(0).getParentId());
//    }
//
//    @Test
//    @DisplayName("사용자가 작성한 댓글 조회")
//    void findCommentByUserId() {
//
//        // given
//        comment3 = Comment.builder()
//                .content("user2가 남기는 post1의 첫 번째 댓글의 대댓글입니다.")
//                .parentId(comment1.getId()).board(post1).user(user2)
//                .build();
//        commentRepository.save(comment3);
//
//        // when
//        List<Long> commentList1 = commentService.findCommentBoardByUserId(user2.getId());
//        List<Long> commentList2 = commentService.findCommentBoardByUserId(user1.getId());
//
//        // then
//        assertEquals(1, commentList1.size());
//        assertEquals(1, commentList2.size());
//        assertEquals(post1.getId(), commentList1.get(0));
//        assertEquals(post2.getId(), commentList2.get(0));
//
//    }
//
//    @Test
//    @DisplayName("댓글 수정")
//    void updateComment() {
//
//        // when
//        Long commentId = commentService.updateCommentByCommentId(comment1.getId(), user2.getId(), "수정된 댓글입니다.");
//
//        // then
//        assertEquals("수정된 댓글입니다.", commentRepository.findById(commentId).get().getContent());
//        assertThrows(CustomException.class, () -> commentService.updateCommentByCommentId(comment2.getId(), user2.getId(), "수정된 댓글입니다."));
//    }
//
////    @Test
////    @DisplayName("댓글 id 기준 댓글 삭제")
////    void deleteCommentByCommentId() {
////
////        // when
////        Long commentId = commentService.deleteCommentByCommentId(comment1.getId(), user2.getId());
////
////        // then
////        assertEquals("삭제된 댓글입니다.", commentRepository.findById(commentId).get().getContent());
////        assertEquals('1', commentRepository.findById(commentId).get().getDeleteYn());
////        assertThrows(CustomException.class, () -> commentService.deleteCommentByCommentId(comment1.getId(), user1.getId()));
////    }
//
//}
