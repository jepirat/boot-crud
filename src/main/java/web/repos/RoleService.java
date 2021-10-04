package web.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;
import java.util.Optional;

public interface RoleService extends JpaRepository<Role, Long> {
    Optional<Role> findById(Long id);
}