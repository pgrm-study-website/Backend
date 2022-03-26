package plming.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import plming.board.domain.PostDTO;

import java.util.List;

@Mapper
public interface PostMapper {

    int insertPost(PostDTO post);

    int updatePost(PostDTO post);

    int deletePost(Long id);

    PostDTO selectPostDetail(Long id);

    List<PostDTO> selectPostList();

    int selectPostTotalCount();

    void clearStore();
}
