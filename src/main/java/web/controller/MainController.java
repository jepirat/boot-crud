package web.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repos.RoleRepo;
import web.repos.UserRepo;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/index")
public class MainController {
    UserRepo userRepo;
    RoleRepo roleRepo;

    public MainController(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public String usersController(Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("userList", users);
        Role[] roles = {new Role("ADMIN"), new Role("USER")};
        model.addAttribute("list",roles);
        return "index";
    }

    @PostMapping
    public String add(@ModelAttribute("user") User user) {
        Role role = new Role("USER");
        roleRepo.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);
        return "redirect:/index";
    }


    @PatchMapping
    public String update(@ModelAttribute("user") User user,
                         @RequestParam(name = "role", required = false) String role) {

        System.out.println(user);
        System.out.println(role);
        Set<Role> roles = userRepo.findById(user.getId()).get().getRole();
        if(roles == null) {
            roles = new HashSet<>();
        }
        if (role != null && role.equals("ADMIN")) {
            Role role3 = new Role(role);
            roleRepo.save(role3);
            roles.add(role3);
        } else if (role != null && role.equals("USER")) {
            if (roles.contains(new Role("ADMIN"))) {
                roles.stream().forEach(rol -> {if (rol.getName().equals("ADMIN")) {
                    roleRepo.delete(rol);}});
            }
            Role role1 = new Role(role);
            roleRepo.save(role1);
            roles.add(role1);
            roles.removeIf(role2 -> role2.getName().equals("ADMIN"));
        }
        user.setRoles(roles);
        userRepo.save(user);
        return "redirect:/index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userRepo.delete(userRepo.findById(id).get());
        return "redirect:/index";
    }
}