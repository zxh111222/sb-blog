package io.github.zxh111222.sbblog.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
