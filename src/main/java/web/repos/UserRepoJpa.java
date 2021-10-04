package web.repos;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

import java.util.List;
import java.util.Optional;
public interface UserRepoJpa extends JpaRepository<User, Long> {
    User findByLogin(String login);
    Optional<User> findById(Long id);
}
