package plming;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.BoardRepository;
import plming.board.service.ApplicationService;
import plming.board.service.BoardService;
import plming.comment.entity.CommentRepository;
import plming.comment.service.CommentService;
import plming.notification.repository.NotificationRepository;
import plming.notification.service.NotificationService;
import plming.user.entity.User;
import plming.user.entity.UserRepository;
import plming.user.service.UserService;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class ServiceIntegrationTest {

    protected User studyMember;
    protected User anotherStudyMember;
    protected User notStudyMember;
    protected User studyAdminMember;
    protected User hasNoResourceMember;
    protected User studyApplyMember;
    protected User notAuthMember;

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected BoardService boardService;
    @Autowired
    protected BoardRepository boardRepository;
    @Autowired
    protected CommentRepository commentRepository;
    @Autowired
    protected CommentService commentService;
    @Autowired
    protected ApplicationRepository applicationRepository;
    @Autowired
    protected ApplicationService applicationService;
    @Autowired
    protected NotificationRepository notificationRepository;
    @Autowired
    protected NotificationService notificationService;
    @Autowired
    protected TestDB testDB;
    @Autowired
    protected EntityManager em;

    @BeforeEach
    void beforeEach() {
        testDB.init();

        studyMember = testDB.findStudyGeneralMember();
        anotherStudyMember = testDB.findAnotherStudyGeneralMember();
        notStudyMember = testDB.findNotStudyMember();
        studyAdminMember = testDB.findStudyAdminMember();
        hasNoResourceMember = testDB.findStudyMemberNotResourceOwner();
        studyApplyMember = testDB.findStudyApplyMember();
    }
}