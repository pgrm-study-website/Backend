package plming.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.board.exception.CustomException;
import plming.board.exception.ErrorCode;
import plming.comment.dto.CommentRequestDto;
import plming.comment.entity.Comment;
import plming.comment.entity.CommentRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Long registerComment(CommentRequestDto params) {

        Board board = boardRepository.findById(params.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User user = userRepository.getById(params.getUserId());
        Comment entity = params.toEntity(board, user);
        commentRepository.save(entity);

        return entity.getId();
    }

    @Override
    public boolean deleteComment(Long id) {
        return false;
    }

    @Override
    public List<Comment> getCommentList(Comment params) {
        return null;
    }
}
