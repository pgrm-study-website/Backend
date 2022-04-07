package plming.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*
     * 204 NO_CONTENT: 요청한 데이터가 없는 경우
     */
    NO_CONTENT(HttpStatus.NO_CONTENT, "요청한 데이터를 찾을 수 없습니다."),

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /*
     * 403 Forbidden: 서버에 요청이 전달되었지만, 권한 때문에 거절된 경우
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "요청 권한이 없습니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /*
     * 406 NOT_ALLOWED: 허용되지 않음. 요청한 페이지가 요청한 콘텐츠 특성으로 응답 불가
     */
    NOT_ACCEPTABLE(HttpStatus.NOT_ACCEPTABLE, "허용되지 않는 요청입니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ;

    private final HttpStatus status;
    private final String message;
}
