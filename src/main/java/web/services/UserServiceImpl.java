package web.services;

import org.springframework.stereotype.Service;
import web.model.User;
import web.repos.UserRepo;
import web.repos.UserRepoIpa;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
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
        userRepo.save(user);
    }

    @Override
    public void delete(User user) {
       userRepo.delete(user);
    }
}
