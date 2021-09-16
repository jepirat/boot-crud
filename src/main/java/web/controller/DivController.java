package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.Role;
import web.model.User;

@Controller
public class DivController {
    @GetMapping("/div")
    public String getGet(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (user.getRoles().contains(new Role("ADMIN"))) {
            model.addAttribute("status", "show");
        } else {
            model.addAttribute("status", "hide");
        }
        return "div";
    }
}
