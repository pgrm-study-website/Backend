package plming.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import plming.board.domain.PostDTO;
import plming.board.service.PostService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts/write")
    public String postWrite(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("post", new PostDTO());
        } else {
            PostDTO post = postService.getPostDetail(id);
            if (post == null) {
                return "redirect: /posts/list";
            }
            model.addAttribute("post", post);
        }

        return "posts/write";
    }

    @PostMapping("/posts/register")
    public String registerPost(final PostDTO post) {
        try {
            boolean isRegistered = postService.registerPost(post);
            if (isRegistered == false) {
                // 게시글 등록에 실패했다는 메시지 전달
            }
        } catch (DataAccessException e) {
            // 데이터베이스 처리 과정에 문제가 발생했다는 메시지 전달
        } catch (Exception e) {
            // 시스템에 문제가 생겼다는 메시지 전달
        }

        return "redirect: /posts/list";
    }
}
