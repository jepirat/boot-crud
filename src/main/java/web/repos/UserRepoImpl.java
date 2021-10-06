package web.repos;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepoImpl  implements UserRepo{
    UserRepoJpa userRepoIpa;
    RoleRepo roleRepo;
    public UserRepoImpl(UserRepoJpa userRepoIpa, RoleRepo roleRepo) {
        this.userRepoIpa = userRepoIpa;
        this.roleRepo = roleRepo;
    }

    @Override
    public User findByLogin(String login) {
        return userRepoIpa.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepoIpa.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepoIpa.findAll();
    }

    @Override
    public void save(User user) {
        Set<Role> roleSet = new HashSet<>();
        Set<Role> roles = user.getRole();
        if (!roles.isEmpty()) {
            roles.stream().forEach(role -> {
                if (roleRepo.findByName(role.getName()) != null) {
                    roleSet.add(roleRepo.findByName(role.getName()));
                } else {
                    roleRepo.save(role);
                    roleSet.add(role);
                }
            });
        } else if (!roles.isEmpty()) {
            Role role = new Role("USER");
            if (roleRepo.findByName(role.getName()) != null) {
                roles.add(roleRepo.findByName(role.getName()));
            } else {
                roleRepo.save(role);
                roles.add(role);
            }
        }
        user.setRoles(roleSet);
        userRepoIpa.save(user);
    }

    @Override
    public void delete(User user) {
        userRepoIpa.delete(user);
    }
}
