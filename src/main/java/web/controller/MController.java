package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repos.UserRepo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/index")
public class MController {
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String usersController(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<User> users = userRepo.findAll();
        if (user.getRoles().contains(new Role("ADMIN"))) {
            model.addAttribute("status", "ADMIN");
        } else {
            model.addAttribute("status", "USER");
        }
        model.addAttribute("userList", users);
        model.addAttribute("emptyUser", new User());
        model.addAttribute("currentUser", user);
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);

        return "index";
    }

    @GetMapping("/new")
    public String show(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        user.setRoles(roles);
        userRepo.save(user);
        return "index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userRepo.findById(id).get());
        return "index";
    }

    @PatchMapping
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(name = "role", required = false) String role,
                         Model model, Authentication authentication) {
        User user1 = (User) authentication.getPrincipal();
        List<User> users = userRepo.findAll();
        if (user1.getRoles().contains(new Role("ADMIN"))) {
            model.addAttribute("status", "ADMIN");
        } else {
            model.addAttribute("status", "USER");
        }
        System.out.println(user);
        System.out.println(role);
        Set<Role> roles = userRepo.findById(user.getId()).get().getRole();
        if (role != null && role.equals("ADMIN")) {
            roles.add(new Role(role));
        } else if (role != null && role.equals("USER")) {
            System.out.println("удаление роли");
            roles.removeIf(role1 -> role1.getName().equals("ADMIN"));
        }
        user.setRoles(roles);
        userRepo.save(user);
        return "index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepo.delete(userRepo.findById(id).get());
        return "index";
    }
}
