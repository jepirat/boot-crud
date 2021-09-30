package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.Role;
import web.model.User;
import web.repos.UserRepo;

@Controller
public class TdController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/td")
    public String test(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getRoles().contains(new Role("ADMIN"))) {
            model.addAttribute("status", "ADMIN");
        } else {
            model.addAttribute("status", "USER");
        }
        model.addAttribute("currentUser", user);
        return "td";
    }
}
