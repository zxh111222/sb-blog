package io.github.zxh111222.sbblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backend")
public class BackendBlogController {
    @GetMapping()
    public String index() {
        return "backend/index";
    }

    @GetMapping("blog")
    public String list() {
        return "backend/backend-list";
    }

    @GetMapping("blog/{id}")
    public String show(@PathVariable String id) {
        System.out.println("id = " + id);
        return "backend/backend-show";
    }
}
