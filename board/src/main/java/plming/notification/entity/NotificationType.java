package plming.notification.entity;

public enum NotificationType {

    APPLY("게시글에 참여 신청이 왔습니다.", "/posts"),
    ACCEPT("게시글에 참여가 승인되었습니다.", "/posts"),
    REJECT("게시글에 참여가 거절되었습니다.", "/posts"),
    COMMENT("에 댓글이 달렸습니다.", "/posts"),
    RECOMMENT("에 대댓글이 달렸습니다.", "/comments"),
    MESSAGE(" 회원에게 메세지가 도착했습니다.", "mail/with");

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
