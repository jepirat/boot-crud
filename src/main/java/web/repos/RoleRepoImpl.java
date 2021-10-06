package web.repos;

import org.springframework.stereotype.Repository;
import web.model.Role;

import java.util.Optional;

@Repository
public class RoleRepoImpl implements RoleRepo{
    RoleRepoJpa roleRepoJpa;

    public RoleRepoImpl(RoleRepoJpa roleRepoJpa) {
        this.roleRepoJpa = roleRepoJpa;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepoJpa.findById(id);
    }

    @Override
    public void save(Role role) {
       roleRepoJpa.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepoJpa.findByName(name);
    }


}
