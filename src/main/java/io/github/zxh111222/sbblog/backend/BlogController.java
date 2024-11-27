package io.github.zxh111222.sbblog.backend;

import io.github.zxh111222.sbblog.Blog;
import io.github.zxh111222.sbblog.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller("BackendBlogController")
@RequestMapping("backend/blog")
public class BlogController {
    @Autowired
    BlogRepository blogRepository;


    @GetMapping()
    public String list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Blog> blogs;
        if (search != null && !search.trim().isEmpty()) {
            blogs = blogRepository.findByTitleContaining(search.trim(), pageable);
        } else {
            blogs = blogRepository.findAll(pageable);
        }
        model.addAttribute("blogs", blogs.getContent());
        model.addAttribute("currentPage", page); // 当前页码
        model.addAttribute("totalPages", blogs.getTotalPages()); // 总页数
        model.addAttribute("totalRecords", blogs.getTotalElements()); //总记录数
        model.addAttribute("search", search);
        return "backend/blog/list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id) {
        System.out.println("id = " + id);
        return "backend/blog/show";
    }

    @GetMapping("add")
    public String add() {
        return "backend/blog/add";
    }

    @Value("${custom.upload.base-path}")
    String uploadBasePath;
    @Value("${custom.upload.cover-path}")
    String coverPath;
    @PostMapping(value = "add")
//    @ResponseBody
    public String save(@RequestParam(value = "coverImage", required = false) MultipartFile file, Blog blog) throws IOException {
        uploadCover(file, blog);

        blogRepository.save(blog);

        return "redirect:/backend/blog";
    }

    private void uploadCover(MultipartFile file, Blog blog) throws IOException {
        if (file != null && !file.isEmpty()) {
            File dir = new File(uploadBasePath + File.separator + coverPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID() + suffix;
            file.transferTo(new File(dir.getAbsolutePath() + File.separator + newFilename));
            blog.setCover("/" + coverPath + File.separator + newFilename);
        }
    }

    @GetMapping("edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("博客不存在"));
        model.addAttribute("blog", blog);
        return "backend/blog/edit";
    }

    @PostMapping("update")
//    @ResponseBody
    public String update(@RequestParam(value = "coverImage", required = false) MultipartFile file, Blog updatedBlog) throws IOException {
        Blog blog = blogRepository.findById(updatedBlog.getId()).orElseThrow(() -> new RuntimeException("博客不存在"));
        blog.setTitle(updatedBlog.getTitle());
        blog.setContent(updatedBlog.getContent());
        System.out.println(file);
        uploadCover(file, blog);
        System.out.println(blog);

        blogRepository.save(blog);
        return "redirect:/backend/blog";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        blogRepository.deleteById(id);
        return "redirect:/backend/blog";
    }
}
