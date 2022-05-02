//package plming.common;
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import plming.board.boardApply.repository.ApplicationRepository;
//import plming.board.board.entity.Board;
//import plming.board.board.repository.BoardRepository;
//import plming.board.boardComment.entity.Comment;
//import plming.board.boardComment.entity.CommentRepository;
//import plming.exception.CustomException;
//import plming.exception.ErrorCode;
//import plming.notification.repository.NotificationRepository;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//@Component
//public class TestDB {
//
//    @Autowired
//    BoardRepository boardRepository;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    CommentRepository commentRepository;
//    @Autowired
//    ApplicationRepository applicationRepository;
//    @Autowired
//    NotificationRepository notificationRepository;
//
//    @Getter
//    private Board board;
//
//    @Transactional
//    public void init() {
//        initMember();
//        initBoard();
//    }
//
//    @Transactional(readOnly = true)
//    public User findGeneralMember() {
//        return userRepository.findByEmail("email1@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findAdminMember() {
//        return userRepository.findByEmail("email2@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findAnotherGeneralMember() {
//        return userRepository.findByEmail("email3@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findNoAuthMember() {
//        return userRepository.findByEmail("email3@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findNotStudyMember() {
//        return userRepository.findByEmail("email4@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findWriteMember() {
//        return userRepository.findByEmail("email5@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    @Transactional(readOnly = true)
//    public User findApplyMember() {
//        return userRepository.findByEmail("email7@gmail.com").orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
//    }
//
//    private void initMember() {
//        User userA = User.builder().nickname("nickname1").email("email1@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userA);
//
//        User userB = User.builder().nickname("nickname2").email("email2@gmail.com").role("ROLE_ADMIN").social(0).build();
//        userRepository.save(userB);
//
//        User userC = User.builder().nickname("nickname3").email("email3@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userC);
//    }
//
//    private void initBoard() {
//        User userD = User.builder().nickname("nickname4").email("email4@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userD);
//
//        User userE = User.builder().nickname("nickname5").email("email5@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userE);
//
//        User userF = User.builder().nickname("nickname6").email("email6@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userF);
//
//        User userG = User.builder().nickname("nickname7").email("email7@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userG);
//
//        User userH = User.builder().nickname("nickname8").email("email8@gmail.com").role("ROLE_USER").social(0).build();
//        userRepository.save(userH);
//
//        Board emptyStudy = Board.builder().user(userD).content("임시용")
//                .period(1).category("스터디").status("모집 중").title("임시용")
//                .participantMax(5)
//                .build();
//        Board study = Board.builder().user(userE).content("백엔드 모집")
//                .period(1).category("스터디").status("모집 중").title("백엔드 모집")
//                .participantMax(5)
//                .build();
//        boardRepository.save(emptyStudy);
//        board = boardRepository.save(study);
//
////        Application application3 = Application.builder()
////                .board(boardRepository.getById(study.getId()))
////                .user(userRepository.getById(userF.getId()))
////                .status("승인")
////                .build();
////        applicationRepository.save(application3);
////
////        Application application4 = Application.builder()
////                .board(boardRepository.getById(study.getId()))
////                .user(userRepository.getById(userG.getId()))
////                .status("대기")
////                .build();
////        applicationRepository.save(application4);
////
////        Application application5 = Application.builder()
////                .board(boardRepository.getById(study.getId()))
////                .user(userRepository.getById(userH.getId()))
////                .status("승인")
////                .build();
////        applicationRepository.save(application5);
//
//        Comment comment1 = Comment.builder()
//                .user(userD)
//                .board(study)
//                .parentId(null)
//                .content("study 첫 번째 댓글입니다.")
//                .build();
//        Comment comment2 = Comment.builder()
//                .user(userE)
//                .board(study)
//                .parentId(null)
//                .content("study 두 번째 댓글입니다.")
//                .build();
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//    }
//
//}
