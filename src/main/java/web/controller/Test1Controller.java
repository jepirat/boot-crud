package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.repos.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class Test1Controller {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/test1")
    public String test(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        model.addAttribute("user", new User());
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);
        return "test1";
    }

    @PostMapping("/test1")
    public String testPost(@RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "login", required = false) String login,
                           @RequestParam(name = "fn", required = false) String fn,
                           @RequestParam(name = "ln", required = false) String ln,
                           @RequestParam(name = "pass", required = false) String pass,
                           @RequestParam(name = "role", required = false) String role,
                           @RequestParam(name = "action", required = false) String action) {
        Set<Role> roles = new HashSet<>();
        if (id != null) {
            roles = userRepo.findById(id).get().getRole();
            if (role != null && role.equals("ADMIN")) {
                roles.add(new Role(role));
            } else if (role != null && role.equals("USER")) {
                System.out.println("удаление роли");
                roles.removeIf(role1 -> role1.getName().equals("ADMIN"));
            }
        }
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setFirstName(fn);
        user.setLastName(ln);
        user.setPassword(pass);
        user.setRoles(roles);
        if (action.equals("delete")) {
            userRepo.delete(user);
        } else {
            userRepo.save(user);
        }
        return "redirect:/test1";
    }
}
