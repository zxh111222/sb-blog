package io.github.zxh111222.sbblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("blog")
public class BlogController {
    @GetMapping()
    public String list(Model model) {
        List<Blog> blogs = new ArrayList<Blog>();
        for (int i = 1; i <= 4; i++) {
            Blog blog = new Blog(Long.parseLong(i + ""), "title-" + i, "content-blablabla" + i);
            blogs.add(blog);
        }
        model.addAttribute("blogs", blogs);
        return "blog/blog-list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id) {
        System.out.println("id = " + id);
        return "blog/blog-show";
    }


}
