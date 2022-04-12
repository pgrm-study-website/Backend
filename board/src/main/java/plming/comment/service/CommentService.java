package plming.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.entity.Board;
import plming.board.entity.BoardRepository;
import plming.comment.dto.CommentOneResponseDto;
import plming.exception.exception.CustomException;
import plming.exception.exception.ErrorCode;
import plming.comment.dto.CommentRequestDto;
import plming.comment.dto.CommentResponseDto;
import plming.comment.dto.RecommentResponseDto;
import plming.comment.entity.Comment;
import plming.comment.entity.CommentRepository;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Long registerComment(final Long boardId, final Long userId, final CommentRequestDto params) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Comment entity = params.toEntity(board, user);
        commentRepository.save(entity);

        return entity.getId();
    }

    public List<CommentResponseDto> findCommentByBoardId(final Long boardId) {

        List<Comment> commentList = commentRepository.findCommentByBoardId(boardId);
        List<CommentResponseDto> result = new ArrayList<>();
        for(Comment comment : commentList) {
            List<RecommentResponseDto> recommentList = toReCommentResponseDto(commentRepository.findRecommentByCommentId(comment.getId()));
            result.add(toCommentResponseDto(comment, recommentList));
        }
        return result;
    }

    public List<Long> findCommentBoardByUserId(final Long userId) {

        return commentRepository.findCommentBoardByUserId(userId);
    }

    private CommentResponseDto toCommentResponseDto(Comment comment, List<RecommentResponseDto> recommentList) {

        return CommentResponseDto.builder().entity(comment).user(comment.getUser())
                .recomment(recommentList).recommentSize(Long.valueOf(recommentList.size()))
                .build();
    }

    private List<RecommentResponseDto> toReCommentResponseDto(List<Comment> recommentList) {

        return recommentList.stream().map(comment -> new RecommentResponseDto(comment, comment.getUser())).collect(Collectors.toList());
    }

    private List<CommentOneResponseDto> toCommentOneResponseDto(List<Comment> list) {

        return list.stream().map(comment -> new CommentOneResponseDto(comment)).collect(Collectors.toList());
    }

    @Transactional
    public Long updateCommentByCommentId(final Long commentId, final Long userId, final String content) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));

        if(comment.getUser().getNickname().equals(user.getNickname())) {
            return commentRepository.updateCommentByCommentId(commentId, content);
        } else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

    @Transactional
    public Long deleteCommentByCommentId(final Long commentId, final Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));

        if(comment.getUser().getNickname().equals(user.getNickname())) {

            if(comment.getDeleteYn() == '1') {
                throw new CustomException(ErrorCode.ALREADY_DELETE);
            }
            return commentRepository.deleteCommentByCommentId(commentId);
        }
        else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}
