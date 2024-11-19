package io.github.zxh111222.sbblog;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    BlogRepository blogRepository;

    @GetMapping()
    public String list(Model model) {
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/blog-list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable String id) {
        System.out.println("id = " + id);
        return "blog/blog-show";
    }


}
