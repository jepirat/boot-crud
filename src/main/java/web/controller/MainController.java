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
public class MainController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/index")
    public String test(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getRoles().contains(new Role("ADMIN"))) {
            model.addAttribute("status", "ADMIN");
        } else {
            model.addAttribute("status", "USER");
        }
        model.addAttribute("currentUser", user);
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        model.addAttribute("user", new User());
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);
        return "index";
    }

    @PostMapping("/index")
    public String testPost(@RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "login", required = false) String login,
                           @RequestParam(name = "fn", required = false) String fn,
                           @RequestParam(name = "ln", required = false) String ln,
                           @RequestParam(name = "pass", required = false) String pass,
                           @RequestParam(name = "role", required = false) String role,
                           @RequestParam(name = "action", required = false) String action) {
        System.out.println(role);
        Set<Role> roles = new HashSet<>();
        if (id != null) {
            roles = userRepo.findById(id).get().getRole();
            if (role != null && role.equals("ADMIN")) {
                roles.add(new Role(role));
            } else if (role != null && role.equals("USER")) {
                System.out.println("удаление роли");
                roles.remove(new Role("ADMIN"));
            }
        }
        roles.add(new Role("USER"));
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
        return "redirect:/index";
    }
}
