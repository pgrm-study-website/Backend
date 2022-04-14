package plming.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserTagRepository extends JpaRepository<UserTag, Long> {
    List<UserTag> findAllByUser(User user);
    void deleteAllByUser(User user);
}