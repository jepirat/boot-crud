package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Role;
import web.model.User;
import web.repos.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ModalController {
    @Autowired
    UserRepo userRepo;
    @GetMapping("/test_modal")
    public String addUser(Model model, Authentication authentication, @RequestParam(name = "userId", required = false) String id) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        List<User> users = userRepo.findAll();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role("ADMIN"));
        model.addAttribute("userList", users);
        model.addAttribute("user", new User(null, "b", "b", "b", "b", roleSet));
        System.out.println(id);
        return "test_modal";
    }

//    @PostMapping("/test_modal")
//    public String getUser(Model model, @RequestParam(name = "userId") Long id) {
//        System.out.println(id);
//        return "test_modal";
//    }
}
