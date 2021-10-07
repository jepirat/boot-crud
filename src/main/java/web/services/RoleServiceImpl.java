package web.services;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.repos.RoleRepo;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    RoleRepo roleRepo;
    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }
    public Optional<Role> findById(Long id) {
        return roleRepo.findById(id);
    }

    @Override
    public void save(Role role) {
       roleRepo.save(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepo.findByName(name);
    }


}
