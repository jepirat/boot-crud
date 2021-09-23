package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OController {
    @GetMapping("/to")
    public String getTo() {
        return "to";
    }

}
