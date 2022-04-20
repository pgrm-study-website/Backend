//package plming.common;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import plming.board.boardApply.repository.ApplicationRepository;
//import plming.board.board.entity.Board;
//import plming.board.board.repository.BoardRepository;
//import plming.board.boardApply.service.ApplicationService;
//import plming.board.board.service.BoardService;
//import plming.board.boardComment.entity.CommentRepository;
//import plming.board.boardComment.service.CommentService;
//import plming.notification.repository.NotificationRepository;
//import plming.notification.service.NotificationService;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//import plming.user.service.UserService;
//
//import javax.persistence.EntityManager;
//
//@SpringBootTest
//@Transactional
//public class ServiceIntegrationTest {
//
//    protected User generalMember;
//    protected User anotherGeneralMember;
//    protected User notStudyMember;
//    protected User studyWriter;
//    protected User hasNoResourceMember;
//    protected User studyApplyMember;
//    protected Board study;
//
//    @Autowired
//    protected UserService userService;
//    @Autowired
//    protected UserRepository userRepository;
//    @Autowired
//    protected BoardService boardService;
//    @Autowired
//    protected BoardRepository boardRepository;
//    @Autowired
//    protected CommentRepository commentRepository;
//    @Autowired
//    protected CommentService commentService;
//    @Autowired
//    protected ApplicationRepository applicationRepository;
//    @Autowired
//    protected ApplicationService applicationService;
//    @Autowired
//    protected NotificationRepository notificationRepository;
//    @Autowired
//    protected NotificationService notificationService;
//    @Autowired
//    protected TestDB testDB;
//    @Autowired
//    protected EntityManager em;
//
//    @BeforeEach
//    void beforeEach() {
//        testDB.init();
//
//        generalMember = testDB.findGeneralMember();
//        anotherGeneralMember = testDB.findAnotherGeneralMember();
//        notStudyMember = testDB.findNotStudyMember();
//        studyWriter = testDB.findWriteMember();
//        hasNoResourceMember = testDB.findNoAuthMember();
//        studyApplyMember = testDB.findApplyMember();
//        study = testDB.getBoard();
//    }
//}