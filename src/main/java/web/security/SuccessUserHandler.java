package web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import web.repos.UserRepo;
import web.model.Role;
import web.model.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserRepo userRepo;
    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
       User user = (User) authentication.getPrincipal();
       Role role = new Role();
       role.setName("ADMIN");
       Role roleUser = new Role();
       roleUser.setName("USER");
        if (user.getRoles().contains(role) && !user.getRoles().contains(roleUser)) {
            System.out.println("Вошел как админ");
            httpServletResponse.sendRedirect("/admin");
        } else if  (user.getRoles().contains(roleUser) && !user.getRoles().contains(role)) {
            httpServletResponse.sendRedirect("/");
            System.out.println("вошел как пользователь");
        } else if  (user.getRoles().contains(roleUser) && user.getRoles().contains(role)) {
            httpServletResponse.sendRedirect("/admin");
            System.out.println("вошел как admin");
        }
    }
}
