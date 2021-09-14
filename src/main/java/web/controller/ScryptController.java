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
public class ScryptController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/ts")
    public String test(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);
        return "ts";
    }

    @PostMapping("/ts")
    public String testPost(@RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "login", required = false) String login,
                           @RequestParam(name = "fn", required = false) String fn,
                           @RequestParam(name = "ln", required = false) String ln,
                           @RequestParam(name = "pass", required = false) String pass,
                           @RequestParam(name = "role", required = false) String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(role));
        userRepo.save(new User(id, login, fn, ln, pass, roles));
        return "redirect:/ts";
    }
}
