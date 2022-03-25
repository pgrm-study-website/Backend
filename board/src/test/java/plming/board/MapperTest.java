package plming.board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import plming.board.domain.PostDTO;
import plming.board.mapper.PostMapper;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    public void createPostTest() {
//        PostDTO post = new PostDTO();
//        post.setTitle("1번 게시글 제목");
//        post.setContent("1번 게시글 제목");
//        post.setUser("사용자1");
//        post.setCategory("study");
//        post.setPeriod("1개월");
//        post.setStatus("모집 중");
//        int result = postMapper.insertPost(post);
//        System.out.println("결과는 " + result + "입니다.");
        for(int i = 3; i <= 50; i++) {
            PostDTO post = new PostDTO();
            post.setTitle(i + "번 게시글 제목");
            post.setContent(i + "번 게시글 내용");
            post.setUser("사용자" + i);
            post.setCategory("study");
            post.setPeriod(i + "개월");
            post.setStatus("모집 중");
            postMapper.insertPost(post);
        }
    }

    @Test
    public void selectPostDetailTest() {
        PostDTO post = postMapper.selectPostDetail((long) 2);
        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule())
                                .writeValueAsString(post);

            System.out.println("=====================");
            System.out.println("boardJson = " + postJson);
            System.out.println("=====================");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePostTest() {
        PostDTO post = new PostDTO();
        post.setTitle("2번 게시글 제목 수정");
        post.setContent("2번 게시글 내용 수정");
        post.setUser("사용자2");
        post.setId((long) 2);

        int result = postMapper.updatePost(post);
        if (result == 1) {
            PostDTO cpPost = postMapper.selectPostDetail((long) 2);
            try {
                String postJson = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .writeValueAsString(cpPost);
                System.out.println("================");
                System.out.println(postJson);
                System.out.println("================");
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deletePostTest() {
        int result = postMapper.deletePost((long) 2);
        if (result == 1) {
            PostDTO post = postMapper.selectPostDetail((long) 2);
            try {
                String postJson = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .writeValueAsString(post);

                System.out.println("======================");
                System.out.println(postJson);
                System.out.println("======================");
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void selectPostListTest() {
        int postTotalCount = postMapper.selectPostTotalCount();
        if (postTotalCount > 0) {
            List<PostDTO> postList = postMapper.selectPostList();
            if (CollectionUtils.isEmpty(postList) == false) {
                for(PostDTO post : postList){
                    System.out.println("=================");
                    System.out.println(post.getTitle());
                    System.out.println(post.getContent());
                    System.out.println(post.getUser());
                    System.out.println("=================");
                }
            }
        }
    }
}

