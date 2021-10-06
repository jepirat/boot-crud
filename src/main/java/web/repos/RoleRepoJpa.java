package web.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepoJpa extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
    Role findByName(String name);
}