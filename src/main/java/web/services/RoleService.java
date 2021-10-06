package web.services;
import web.model.Role;
import java.util.Optional;
public interface RoleService {
    public Optional<Role> findById(Long id);
    void save(Role role);
    Role findByName(String name);
}
