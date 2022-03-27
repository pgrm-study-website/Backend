package plming.board.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PostDTO {

    /** 게시글 번호 (PK) */
    private Long id;

    /** 작성자  */
    private String user;

    /** 카테고리 */
    private String category;

    /** 모집 상태 */
    private String status;

    /** 진행 기간 */
    private String period;

    /** 제목 */
    private String title;

    /** 내용 */
    private String content;

    /** 조회수 */
    private Long viewCnt;

    /** 참여 인원 */
    private Integer participantNum;

    /** 생성 시간 */
    private LocalDateTime createDate;

    /** 수정 시간 */
    private LocalDateTime updateDate;

    /** 삭제 여부 */
    private String deleteYn;

    public PostDTO() {
    }
}
