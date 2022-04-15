package plming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Application;
import plming.board.entity.ApplicationRepository;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.comment.entity.Comment;
import plming.comment.entity.CommentRepository;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.notification.repository.NotificationRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

@Component
public class TestDB {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    NotificationRepository notificationRepository;

    @Transactional
    public void init() {
        initMember();
        initBoard();
    }

    @Transactional(readOnly = true)
    public User findGeneralMember() {
        return userRepository.findByEmail("email1@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findAnotherStudyGeneralMember() {
        return userRepository.findByEmail("email3@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findNoAuthMember() {
        return userRepository.findByEmail("email2@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findAdminMembet() {
        return userRepository.findByEmail("email4@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    @Transactional
    public User findNotStudyMember() {
        return userRepository.findByEmail("email1@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    public User findStudyApplyMember() {
        return userRepository.findByEmail("email10@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    public User findStudyMemberNotResourceOwner() {
        return userRepository.findByEmail("email11@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    public User findStudyGeneralMember() {
        return userRepository.findByEmail("email0@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public User findStudyAdminMember() {
        return userRepository.findByEmail("email5@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
    }

    private void initMember() {
        User userA = User.builder().nickname("nickname1").email("email1@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userA);

        User userB = User.builder().nickname("nickname2").email("email2@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userB);

        User userC = User.builder().nickname("nickname4").email("email4@gmail.com").role("ROLE_ADMIN").social(0).build();
        userRepository.save(userC);
    }

    private void initBoard() {
        User userA = User.builder().nickname("nickname3").email("email3@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userA);

        User userB = User.builder().nickname("nickname5").email("email5@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userB);

        User userC = User.builder().nickname("nickname0").email("email0@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userC);

        User userD = User.builder().nickname("nickname10").email("email10@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userD);

        User userE = User.builder().nickname("nickname11").email("email11@gmail.com").role("ROLE_USER").social(0).build();
        userRepository.save(userE);

        Board emptyStudy = Board.builder().user(userB).content("임시용")
                .period(1).category("스터디").status("모집 중").title("임시용")
                .participantMax(5)
                .build();
        Board study = Board.builder().user(userA).content("백엔드 모집")
                .period(1).category("스터디").status("모집 중").title("백엔드 모집")
                .participantMax(5)
                .build();
        boardRepository.save(emptyStudy);
        boardRepository.save(study);

        Application application3 = Application.builder()
                .board(boardRepository.getById(study.getId()))
                .user(userRepository.getById(userC.getId()))
                .status("승인")
                .build();
        applicationRepository.save(application3);

        Application application4 = Application.builder()
                .board(boardRepository.getById(study.getId()))
                .user(userRepository.getById(userD.getId()))
                .status("대기")
                .build();
        applicationRepository.save(application4);

        Application application5 = Application.builder()
                .board(boardRepository.getById(study.getId()))
                .user(userRepository.getById(userE.getId()))
                .status("승인")
                .build();
        applicationRepository.save(application5);

        Comment comment1 = Comment.builder()
                .user(userC)
                .board(study)
                .parentId(null)
                .content("study 첫 번째 댓글입니다.")
                .build();
        Comment comment2 = Comment.builder()
                .user(userA)
                .board(study)
                .parentId(null)
                .content("study 두 번째 댓글입니다.")
                .build();
        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }

}
