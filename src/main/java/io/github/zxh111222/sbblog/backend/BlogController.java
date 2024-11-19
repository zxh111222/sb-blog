package io.github.zxh111222.sbblog.backend;

import io.github.zxh111222.sbblog.Blog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("BackendBlogController")
@RequestMapping("backend/blog")
public class BlogController {
    @GetMapping()
    public String list() {
        return "backend/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id) {
        System.out.println("id = " + id);
        return "backend/show";
    }

    @GetMapping("add")
    public String add() {
        return "backend/add";
    }

    @PostMapping(value = "add")
//    @ResponseBody
    public String save(Blog blog) {
        System.out.println(blog);
        System.out.println("接收到 Post 请求");
        return "redirect:/backend/blog";
    }

    @GetMapping("edit")
    public String edit() {
        return "backend/edit";
    }

    @PostMapping("edit")
    @ResponseBody
    public String update() {
        System.out.println("接收到 Post 请求");
        return "修改成功！";
    }
}
