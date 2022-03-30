package plming.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

@SpringBootTest
public class RelationMappingTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testManyToOneInsert() {
        User user1 = User.builder()
                .email("email@email.com")
                .github("github")
                .image("no image")
                .introduce("introduce")
                .nickname("nickname")
                .password("password")
                .role("ROLE_USER")
                .social(1)
                .build();
        userRepository.save(user1);

        User user2 = User.builder()
                .email("email2@email.com")
                .github("github2")
                .image("no image")
                .introduce("introduce2")
                .nickname("nickname2")
                .password("password2")
                .role("ROLE_ADMIN")
                .social(1)
                .build();

        userRepository.save(user2);

        for(int i = 1; i<=3; i++) {
            Board post = Board.builder()
                    .user(user1)
                    .content("사용자1의 " + i + "번째 게시글입니다.")
                    .period(i + "개월")
                    .category("스터디")
                    .status("모집 중")
                    .title("사용자1의 게시글" + i)
                    .build();
            boardRepository.save(post);
        }

        for(int i = 1; i<=3; i++) {
            Board post = Board.builder()
                    .user(user2)
                    .content("사용자2의 " + i + "번째 게시글입니다.")
                    .period(i + "개월")
                    .category("프로젝트")
                    .status("모집 중")
                    .title("사용자2의 게시글" + i)
                    .build();
            boardRepository.save(post);
        }
    }

    @Test
    public void userTest() {

        Long userId = 4L;
        System.out.println(userRepository.findById(userId));

    }
}
