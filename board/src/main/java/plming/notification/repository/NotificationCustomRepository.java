package plming.notification.repository;

import org.apache.ibatis.annotations.Param;
import plming.notification.entity.Notification;

import java.util.List;

public interface NotificationCustomRepository {

    List<Notification> findAllByUserId(@Param("userId") Long userId);

    Long countUnReadNotification(@Param("userId") Long userId);
}
