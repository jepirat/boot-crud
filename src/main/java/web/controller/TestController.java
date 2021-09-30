package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/")
    public String testRest() {
        return "test";
    }

    @GetMapping("/test")
    public String test1Rest() {
        return "test";
    }
}
