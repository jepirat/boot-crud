package web.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;
import web.model.User;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
}