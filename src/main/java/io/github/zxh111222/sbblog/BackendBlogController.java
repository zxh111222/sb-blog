package io.github.zxh111222.sbblog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("backend")
public class BackendBlogController {
    @GetMapping()
    public String index() {
        return "backend/index";
    }

    @GetMapping("blog")
    public String list() {
        return "backend/list";
    }

    @GetMapping("blog/{id}")
    public String show(@PathVariable String id) {
        System.out.println("id = " + id);
        return "backend/show";
    }

    @GetMapping("blog/add")
    public String add() {
        return "backend/add";
    }

    @PostMapping("blog/add")
    @ResponseBody
    public String save() {
        System.out.println("接收到 Post 请求");
        return "保存成功！";
    }

    @GetMapping("blog/edit")
    public String edit() {
        return "backend/edit";
    }

    @PostMapping("blog/edit")
    @ResponseBody
    public String update() {
        System.out.println("接收到 Post 请求");
        return "修改成功！";
    }
}
