package web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.pojo.UserProxy;
import web.services.RoleService;
import web.services.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("users")
public class RestCont {
    final UserService userService;
    final RoleService roleService;

    public RestCont(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public List<UserProxy> set() {
        List<UserProxy> userPojos = new ArrayList<>();
        List<User> users = userService.findAll();
        users.stream().forEach(u -> userPojos.add(new UserProxy(u)));
        return userPojos;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public UserProxy newUser(@RequestBody @Valid User user) {
        user.getRole().forEach(role -> roleService.save(role));
        user.setId(null);
        userService.save(user);
        return new UserProxy(user);
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public UserProxy patchUser(@RequestBody @Valid User user) {

        user.getRole().forEach(role -> roleService.save(role));
        userService.save(user);
        return new UserProxy(user);
    }

    @DeleteMapping("/{id}")
    public UserProxy delete(@PathVariable("id") long id) {
        UserProxy userProxy = new UserProxy(userService.findById(id).get());
        userService.delete(userService.findById(id).get());
        return userProxy;
    }
}
