package web.repos;
import web.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepo {
    User findByLogin(String login);
    Optional<User> findById(Long id);
    List<User> findAll();
    void save(User user);
    void delete(User user);

}
