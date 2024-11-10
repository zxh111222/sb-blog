package io.github.zxh111222.sbblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("blog")
public class BlogController {
    @GetMapping()
    public String list() {
        return "blog/blog-list";
    }




}
