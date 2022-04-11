package plming.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickname(String nickName);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickName);
    Optional<User> findByEmail(String email);
}
