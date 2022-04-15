package plming.notification.exception;

public class InvalidNotificationContentException extends RuntimeException {

    public InvalidNotificationContentException() {

    }

    public InvalidNotificationContentException(String message) {
        super(message);
    }

    public InvalidNotificationContentException(String message, Throwable cause) {
        super(message, cause);
    }
}
