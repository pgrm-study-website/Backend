package plming.exception;

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
     * 400 BAD_REQUEST: 이메일 인증코드 불일치
     */
    BAD_REQUEST_EMAIL(HttpStatus.BAD_REQUEST,"인증코드가 틀렸습니다."),

    /*
     * 400 BAD_REQUEST: 닉네임 중복
     */
    NICKNAME_OVERLAP(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),

    /*
     * 400 BAD_REQUEST: 이메일 중복
     */
    EMAIL_OVERLAP(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),

    /*
     * 400 BAD_REQUEST: 유저 태그 개수 초과
     */
    USER_TAG_EXCESS(HttpStatus.BAD_REQUEST,"유저 태그 개수를 초과하였습니다."),

    /*
     * 401 UNAUTHORIZED: 로그인 실패(이메일 없음)
     */
    LOGIN_UNAUTHORIZED_EMAIL(HttpStatus.UNAUTHORIZED, "존재하지 않는 이메일입니다."),

    /*
     * 401 UNAUTHORIZED: 로그인 실패(비밀번호 틀림)
     */
    LOGIN_UNAUTHORIZED_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    /*
     * 401 UNAUTHORIZED: 로그인 실패
     */
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "계정정보가 잘못되었습니다."),

    /*
     * 403 FORBIDDEN: 권한이 없음
     */
    FORBIDDEN(HttpStatus.FORBIDDEN,"요청 권한이 없습니다."),

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    ALREADY_DELETE(HttpStatus.BAD_REQUEST, "이미 삭제되었습니다."),

    /*
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_SEARCH(HttpStatus.BAD_REQUEST, "잘못된 검색 입니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),

    /*
     * 404 NOT_FOUND: 파일을 찾을 수 없음
     */
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자 정보를 찾을 수 없습니다."),

    /*
     * 404 NOT_FOUND: 메시지 찾을 수 없음
     */
    MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "메시지 정보를 찾을 수 없습니다."),

    /*
     * 404 NOT_FOUND: 이메일 인증코드 확인 불가
     */
    EMAIL_CODE_NOT_FOUND(HttpStatus.NOT_FOUND,"인증코드를 확인할 수 없습니다."),

    /*
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    COMMENTS_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글 정보를 찾을 수 없습니다."),

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