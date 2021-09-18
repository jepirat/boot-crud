package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.model.User;
import web.pojo.UsesProxy;
import web.repos.UserRepo;

import java.util.*;

@RestController
@RequestMapping("users")
public class RestCont {
    @Autowired
    UserRepo userRepo;
    @GetMapping
    public List<UsesProxy> set() {
        List<UsesProxy> userPojos = new ArrayList<>();
        List<User> users = userRepo.findAll();
        users.stream().forEach(u -> userPojos.add(new UsesProxy(u)));
        return userPojos;
    }
}
