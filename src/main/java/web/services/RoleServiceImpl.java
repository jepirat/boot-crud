package web.services;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.repos.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl {
    RoleService roleRepo;

    public RoleServiceImpl(RoleService roleRepo) {
        this.roleRepo = roleRepo;
    }
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }
}
