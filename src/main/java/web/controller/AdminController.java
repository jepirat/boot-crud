package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.repos.UserRepo;
import web.model.Role;
import web.model.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String usersController(Model model, Authentication authentication) {
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);
        return "admin/index";
    }

    @GetMapping("/new")
    public String show(Model model) {
        model.addAttribute("user", new User());
        return "admin/new";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        user.setRoles(roles);
        userRepo.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);
        model.addAttribute("user", userRepo.findById(id).get());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam(name = "role", required = false) String role) {
        System.out.println(role);
        Set<Role> roles = userRepo.findById(id).get().getRole();
        if (role != null && role.equals("ADMIN")) {
            roles.add(new Role(role));
        } else if (role != null && role.equals("USER")) {
            System.out.println("удаление роли");
            roles.removeIf(role1 -> role1.getName().equals("ADMIN"));
        }
        user.setRoles(roles);
        userRepo.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepo.delete(userRepo.findById(id).get());
        return "redirect:/admin";
    }
}