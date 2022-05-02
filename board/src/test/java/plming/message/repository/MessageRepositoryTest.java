//package plming.message.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import plming.exception.CustomException;
//import plming.exception.ErrorCode;
//import plming.message.entity.Message;
//import plming.message.entity.MessageRepository;
//import plming.user.entity.User;
//import plming.user.entity.UserRepository;
//
//import java.util.List;
//
//@SpringBootTest
//class MessageRepositoryTest {
//    @Autowired
//    MessageRepository messageRepository;
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    void findMessageAllListByUserId() {
//        User user = userRepository.findById(25L).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
//        List<Message> list = messageRepository.findMessageGroupByUser(user);
//        for(Message message : list){
//            System.out.println(message.toString());
//        }
//    }
//
//    @Test
//    void findMessageListByUserAndOther() {
//        User user = userRepository.findById(25L).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
//        User other = userRepository.findById(27L).orElseThrow(()-> new CustomException(ErrorCode.USERS_NOT_FOUND));
//        List<Message> list = messageRepository.findMessageByUserAndOther(user,other);
//        for(Message message : list){
//            System.out.println(message.toString());
//        }
//    }
//
//}