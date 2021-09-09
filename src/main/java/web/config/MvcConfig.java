package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.repos.UserRepo;

@Configuration
@EntityScan("web.model")
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    UserRepo userRepo;
    public void addViewControllers(ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.addViewController("/login").setViewName("login");
    }
}