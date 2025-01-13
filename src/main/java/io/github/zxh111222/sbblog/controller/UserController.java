package io.github.zxh111222.sbblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("dashboard")
    public String dashboard(){
        return "/user/dashboard";
    }
}
