//package plming.common;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.context.WebApplicationContext;
//import plming.board.entity.Board;
//import plming.board.service.ApplicationService;
//import plming.board.service.BoardService;
//import plming.common.TestDB;
//import plming.auth.service.JwtTokenProvider;
//import plming.board.entity.ApplicationRepository;
//import plming.board.entity.BoardRepository;
//import plming.notification.repository.NotificationRepository;
//import plming.notification.service.NotificationService;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//
//@SpringBootTest
//@Transactional
//@AutoConfigureMockMvc
//public class ControllerIntegrationTest {
//
//    protected Board study;
//    protected User studyMember;
//    protected User anotherStudyMember;
//    protected User notStudyMember;
//    protected User webAdminMember;
//    protected User studyAdminMember;
//    protected User hasNoResourceMember;
//    protected User studyApplyMember;
//    protected User studyWriter;
//
//    @Autowired
//    protected WebApplicationContext context;
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected JwtTokenProvider jwtTokenProvider;
//    @Autowired
//    protected UserRepository memberRepository;
//    @Autowired
//    protected BoardRepository boardRepository;
//    @Autowired
//    protected BoardService boardService;
//    @Autowired
//    protected ApplicationRepository applicationRepository;
//    @Autowired
//    protected NotificationRepository notificationRepository;
//    @Autowired
//    protected NotificationService notificationService;
//    @Autowired
//    protected TestDB testDB;
//
//    @BeforeEach
//    void beforeEach() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
//        testDB.init();
//
//        studyMember = testDB.findGeneralMember();
//        anotherStudyMember = testDB.findAnotherGeneralMember();
//        notStudyMember = testDB.findNotStudyMember();
//        webAdminMember = testDB.findAdminMember();
//        studyAdminMember = testDB.findAdminMember();
//        studyWriter = testDB.findWriteMember();
//        hasNoResourceMember = testDB.findNoAuthMember();
//        studyApplyMember = testDB.findApplyMember();
//        study = testDB.getBoard();
//    }
//}