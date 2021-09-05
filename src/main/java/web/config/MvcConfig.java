package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.model.Role;
import web.model.User;
import web.repos.UserRepo;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EntityScan("web.model")
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    UserRepo userRepo;
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
//        Set<Role> roles = new HashSet<>();
//       roles.add(new Role("USER"));
//       User user = new User("u", "u", "u", "a", roles);
//       userRepo.save(user);
        viewControllerRegistry.addViewController("/login").setViewName("login");
    }
}