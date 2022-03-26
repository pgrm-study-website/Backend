package plming.board.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plming.board.domain.PostDTO;
import plming.board.mapper.PostMapper;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {

    @Autowired
    PostServiceImpl postService;

    @Autowired
    private PostMapper postMapper;

    private PostDTO post = new PostDTO();

    @BeforeEach
    void beforeEach() {
        post.setTitle("1번 게시글 제목");
        post.setContent("1번 게시글 제목");
        post.setUser("사용자1");
        post.setCategory("study");
        post.setPeriod("1개월");
        post.setStatus("모집 중");
    }

    @AfterEach
    void afterEach() {
        postMapper.clearStore();
    }

    @Test
    @DisplayName("게시글 등록 테스트")
    void registerPostTest() {
        // given
        // when
        boolean result = postService.registerPost(post);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("게시글 하나 가져오기")
    void getPostDetailTest() {
        // given
        postService.registerPost(post);

        // when
        PostDTO postCp = postService.getPostDetail(post.getId());

        // then
        assertEquals(postCp.getTitle(), post.getTitle());
    }

    @Test
    void deletePostTest() {
        // given
        postService.registerPost(post);

        // when
        boolean result = postService.deletePost(post.getId());

        // then
        assertThat(result).isTrue();
        assertEquals(postMapper.selectPostTotalCount(), 0);
    }

    @Test
    void getPostListTest() {
        // given
        PostDTO post2 = new PostDTO();
        post2.setTitle("2번 게시글 제목");
        post2.setContent("2번 게시글 제목");
        post2.setUser("사용자2");
        post2.setCategory("study");
        post2.setPeriod("2개월");
        post2.setStatus("모집 중");

        postService.registerPost(post);
        postService.registerPost(post2);

        // when
        List<PostDTO> postList = Collections.emptyList();
        postList = postService.getPostList();

        // then
        assertEquals(postList.size(), 2);
    }
}
