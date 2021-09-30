package web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;

@Controller
public class Test1Controller {
    @GetMapping("/test1")
    public String getTo(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        return "test1";
    }
}
