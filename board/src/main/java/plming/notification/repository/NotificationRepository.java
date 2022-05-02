package plming.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plming.notification.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {

}
