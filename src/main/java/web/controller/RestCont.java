package web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.pojo.UserProxy;
import web.repos.RoleRepo;
import web.repos.UserRepo;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("users")
public class RestCont {
    final UserRepo userRepo;
    final RoleRepo roleRepo;

    public RestCont(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @GetMapping
    public List<UserProxy> set() {
        List<UserProxy> userPojos = new ArrayList<>();
        List<User> users = userRepo.findAll();
        users.stream().forEach(u -> userPojos.add(new UserProxy(u)));
        return userPojos;
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public UserProxy patchUser(@RequestBody @Valid User user) {
        user.getRole().forEach(role -> roleRepo.save(role));
        userRepo.save(user);
        System.out.println(user);
        return new UserProxy(user);
    }

    @DeleteMapping("/{id}")
    public UserProxy delete(@PathVariable("id") long id) {
        UserProxy userProxy = new UserProxy(userRepo.findById(id).get());
        userRepo.delete(userRepo.findById(id).get());
        return userProxy;
    }
}
