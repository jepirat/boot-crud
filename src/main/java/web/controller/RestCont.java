package web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.pojo.UsesProxy;
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
    public List<UsesProxy> set() {
        List<UsesProxy> userPojos = new ArrayList<>();
        List<User> users = userRepo.findAll();
        users.stream().forEach(u -> userPojos.add(new UsesProxy(u)));
        return userPojos;
    }

    @RequestMapping(value = "", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public ResponseEntity<User> patchUser(@RequestBody @Valid User user) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.getRole().forEach(role -> roleRepo.save(role));
        userRepo.save(user);
        System.out.println(user);
        return new  ResponseEntity<>(user, httpHeaders, HttpStatus.CREATED);
    }
}
