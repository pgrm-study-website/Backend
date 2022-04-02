package plming.user.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByIdAndPassword(Long id, String password);
}