package plming.notification.entity;

<<<<<<< HEAD
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
=======
>>>>>>> 0a98514152bd4537a29ad2a539acda888e95a9dc
public enum NotificationType {

    apply("게시글에 참여 신청이 왔습니다.", "/posts"),
    accept("게시글에 참여가 승인되었습니다.", "/posts"),
    reject("게시글에 참여가 거절되었습니다.", "/posts"),
    comment("에 댓글이 달렸습니다.", "/posts"),
    recomment("에 대댓글이 달렸습니다.", "/comments"),
    message(" 회원에게 메세지가 도착했습니다.", "mail/with");

    private String content;
    private String url;

    NotificationType(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public String makeContent(String title) {
        return "'" + title + "'" + content;
    }

    public String makeUrl(Long id) {
        return url + "/" + id;
    }
}
