package plming.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.tag.entity.TagRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RelationMappingTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void BoardTagTest() {
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

        Board post1 = Board.builder()
                .user(user1)
                .content("사용자1의 첫 번째 게시글입니다.")
                .period("1개월")
                .category("스터디")
                .status("모집 중")
                .title("사용자1의 게시글1")
                .build();
        boardRepository.save(post1);

        Board post2 = Board.builder()
                .user(user2)
                .content("사용자2의 첫 번째 게시글입니다.")
                .period("1개월")
                .category("프로젝트")
                .status("모집 중")
                .title("사용자2의 게시글 1")
                .build();
        boardRepository.save(post2);

    }

    @Test
    void findAll() {
        // when
        List<Board> boardList = boardRepository.findAll();

        // then
        assertEquals(boardRepository.count(), boardList.size());
    }

    @Test
    void delete() {
        // given
        Board post = boardRepository.findById(35L).get();

        // when
        boardRepository.delete(post);

        // then
        assertEquals(4, boardRepository.count());
    }

    @Test
    public void userTest() {

        Long userId = 4L;
        System.out.println(userRepository.findById(userId));

    }
}
