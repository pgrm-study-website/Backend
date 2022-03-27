package plming.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plming.board.domain.PostDTO;
import plming.board.mapper.PostMapper;

import java.util.Collections;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public boolean registerPost(PostDTO post) {
        int queryResult = 0;

        if (post.getId() == null) {
            queryResult = postMapper.insertPost(post);
        }
        else {
            queryResult = postMapper.updatePost(post);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public PostDTO getPostDetail(Long id) {
        return postMapper.selectPostDetail(id);
    }

    @Override
    public boolean deletePost(Long id) {
        int queryResult = 0;
        PostDTO post = postMapper.selectPostDetail(id);

        if (post != null && "N".contains(post.getDeleteYn())) {
            queryResult = postMapper.deletePost(id);
        }

        return (queryResult == 1) ? true : false;
    }

    @Override
    public List<PostDTO> getPostList() {
        List<PostDTO> postList = Collections.emptyList();
        int postTotalCount = postMapper.selectPostTotalCount();

        if (postTotalCount > 0) {
            postList = postMapper.selectPostList();
        }

        return postList;
    }
}
