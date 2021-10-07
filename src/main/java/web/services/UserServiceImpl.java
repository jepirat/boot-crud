package web.services;

import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;
import web.repos.RoleRepo;
import web.repos.UserRepo;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;
    private RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public void save(User user) {
        Set<Role> rolesToSave = new HashSet<>();
        Set<Role> rolesFromUser = user.getRole();
        List<Role> rolesFromDb = roleRepo.findAll();
            rolesFromUser.forEach(role ->
            {if (rolesFromDb.contains(role)) {
                rolesToSave.add(rolesFromDb.get(rolesFromDb.indexOf(role)));
            } if (!rolesFromDb.contains(role)) {
                rolesFromUser.forEach(role1 ->
                {roleRepo.save(role1);
                rolesToSave.add(role1);});
            }
            });
            user.setRoles(rolesToSave);
            userRepo.save(user);
    }
    @Override
    public void delete(User user) {
       userRepo.delete(user);
    }
}
