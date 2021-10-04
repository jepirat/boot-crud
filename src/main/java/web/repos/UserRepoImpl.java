package web.repos;
import org.springframework.stereotype.Repository;
import web.model.User;
import java.util.List;
import java.util.Optional;
@Repository
public class UserRepoImpl  implements UserRepo{
    UserRepoJpa userRepoIpa;

    public UserRepoImpl(UserRepoJpa userRepoIpa) {
        this.userRepoIpa = userRepoIpa;
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
         userRepoIpa.save(user);
    }

    @Override
    public void delete(User user) {
        userRepoIpa.delete(user);
    }
}
