package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import web.model.Role;
import web.model.User;
import web.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EntityScan("web.model")
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    UserService userService;

    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/login").setViewName("login");
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect(){
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(new Role("ADMIN"));
//        User user = new User("admin", "a", "a", "a", roleSet);
//        userService.save(user);
        return new SpringSecurityDialect();
    }
}