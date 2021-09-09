package web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.repos.UserRepo;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/")
    public String index() {
        return "redirect:/user";
    }
    @GetMapping("/test")
    public String test(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        model.addAttribute("user", new User());
        return "test";
    }
    @GetMapping("/user")
    public String hello(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
  }

}
