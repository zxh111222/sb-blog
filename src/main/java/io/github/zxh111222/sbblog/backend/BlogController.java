package io.github.zxh111222.sbblog.backend;

import io.github.zxh111222.sbblog.Blog;
import io.github.zxh111222.sbblog.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("BackendBlogController")
@RequestMapping("backend/blog")
public class BlogController {
    @Autowired
    BlogRepository blogRepository;


    @GetMapping()
    public String list(Model model) {
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);
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
        blogRepository.save(blog);
        return "redirect:/backend/blog";
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("博客不存在"));
        model.addAttribute("blog", blog);
        return "backend/edit";
    }

    @PostMapping("edit")
//    @ResponseBody
    public String update(Blog updatedBlog) {
        Blog blog = blogRepository.findById(updatedBlog.getId()).orElseThrow(() -> new RuntimeException("博客不存在"));
        blog.setTitle(updatedBlog.getTitle());
        blog.setContent(updatedBlog.getContent());
        blogRepository.save(blog);
        return "redirect:/backend/blog";
    }
}
