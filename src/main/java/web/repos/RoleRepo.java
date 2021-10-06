package web.repos;

import web.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepo {
    Optional<Role> findById(Long id);
    void save(Role role);
    Role findByName(String name);
}
