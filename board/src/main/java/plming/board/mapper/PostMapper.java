package plming.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import plming.board.domain.PostDTO;

import java.util.List;

@Mapper
public interface PostMapper {

    public int insertPost(PostDTO post);

    public int updatePost(PostDTO post);

    public int deletePost(Long id);

    public PostDTO selectPostDetail(Long id);

    public List<PostDTO> selectPostList();

    public int selectPostTotalCount();
}
