package plming.board.service;

import plming.board.domain.PostDTO;

import java.util.List;

public interface PostService {

    public boolean registerPost(PostDTO post);

    public PostDTO getPostDetail(Long id);

    public boolean deletePost(Long id);

    public List<PostDTO> getPostList();
}
