package plming.board.boardComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plming.board.board.entity.Board;
import plming.board.board.repository.BoardRepository;
import plming.board.boardComment.entity.Comment;
import plming.board.boardComment.entity.CommentRepository;
import plming.board.boardComment.dto.CommentOneResponseDto;
import plming.event.CommentCreateEvent;
import plming.exception.CustomException;
import plming.exception.ErrorCode;
import plming.board.boardComment.dto.CommentRequestDto;
import plming.board.boardComment.dto.CommentResponseDto;
import plming.board.boardComment.dto.RecommentResponseDto;
import plming.notification.entity.NotificationType;
import plming.user.entity.User;
import plming.user.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public Long registerComment(final Long boardId, final Long userId, final CommentRequestDto params) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Comment entity = params.toEntity(board, user);
        commentRepository.save(entity);

        if(commentRepository.getById(entity.getId()).getParentId() == null) {
            eventPublisher.publishEvent(new CommentCreateEvent(entity, entity.getBoard().getUser(), NotificationType.comment));
        } else {
            Comment comment = commentRepository.getById(entity.getParentId());
            eventPublisher.publishEvent(new CommentCreateEvent(entity, comment.getUser(), NotificationType.recomment));
        }

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
    public void deleteCommentByCommentId(final Long commentId, final Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USERS_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));

        if(comment.getUser().getNickname().equals(user.getNickname())) {

            if(comment.getDeleteYn() == '1') {
                throw new CustomException(ErrorCode.ALREADY_DELETE);
            }
            commentRepository.deleteCommentByCommentId(commentId);
        }
        else {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }
}