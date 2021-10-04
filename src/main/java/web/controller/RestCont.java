package web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.pojo.UserProxy;
import web.repos.RoleService;
import web.services.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("users")
public class RestCont {
    final UserService userRepo;
    final RoleService roleRepo;

    public RestCont(UserService userService, RoleService roleService) {
        this.userRepo = userService;
        this.roleRepo = roleService;
    }

    @GetMapping
    public List<UserProxy> set() {
        List<UserProxy> userPojos = new ArrayList<>();
        List<User> users = userRepo.findAll();
        users.stream().forEach(u -> userPojos.add(new UserProxy(u)));
        return userPojos;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public UserProxy newUser(@RequestBody @Valid User user) {
        user.getRole().forEach(role -> roleRepo.save(role));
        user.setId(null);
        userRepo.save(user);
        return new UserProxy(user);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public UserProxy patchUser(@RequestBody @Valid User user) {

        user.getRole().forEach(role -> roleRepo.save(role));
        userRepo.save(user);
        return new UserProxy(user);
    }

    @DeleteMapping("/{id}")
    public UserProxy delete(@PathVariable("id") long id) {
        UserProxy userProxy = new UserProxy(userRepo.findById(id).get());
        userRepo.delete(userRepo.findById(id).get());
        return userProxy;
    }
}
