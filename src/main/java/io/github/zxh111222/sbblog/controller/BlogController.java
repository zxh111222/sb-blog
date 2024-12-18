package io.github.zxh111222.sbblog.controller;

import io.github.zxh111222.sbblog.entity.Blog;
import io.github.zxh111222.sbblog.dao.BlogRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("blog")
public class BlogController {

    @Autowired
    BlogRepository blogRepository;

    @GetMapping()
    public String list(Model model,
                       @RequestParam(value = "page") Optional<Integer> page,
                       @RequestParam(value = "size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("id").descending());

        Page<Blog> pageContent = blogRepository.findAll(pageable);

        model.addAttribute("page", pageContent);
        return "blog/blog-list";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);

        List<String> contentBlocks = splitContent(String.valueOf(optionalBlog.get().getContent()), 300);
        model.addAttribute("content_blocks", contentBlocks);


        if (optionalBlog.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            model.addAttribute("blog", optionalBlog.get());
            return "blog/blog-show";
        }
    }

    public List<String> splitContent(String content, int maxWords) {
        List<String> blocks = new ArrayList<>();
        String[] lines = content.split("\n"); // 按行分割
        StringBuilder currentBlock = new StringBuilder();
        boolean insideCodeBlock = false; // 用于检测是否在代码块内

        for (String line : lines) {
            // 检测是否是代码块的开始或结束
            if (line.startsWith("```")) {
                insideCodeBlock = !insideCodeBlock; // 切换代码块状态
                if (!insideCodeBlock) {
                    // 结束代码块时，加入当前块内容
                    currentBlock.append(line).append("\n");
                    blocks.add(currentBlock.toString().trim());
                    currentBlock.setLength(0); // 清空当前块
                } else {
                    // 开始新的代码块时，加入当前块并开始新的块
                    if (currentBlock.length() > 0) {
                        blocks.add(currentBlock.toString().trim());
                        currentBlock.setLength(0);
                    }
                    currentBlock.append(line).append("\n");
                }
            } else if (insideCodeBlock) {
                // 如果在代码块内，直接添加内容，不分割
                currentBlock.append(line).append("\n");
            } else if (line.startsWith("#")) {
                // 处理标题行
                if (currentBlock.length() > 0) {
                    blocks.add(currentBlock.toString().trim()); // 将上一块内容加入到 blocks
                    currentBlock.setLength(0); // 清空当前块
                }
                // 将标题行与接下来的文本内容一起加入到当前块
                currentBlock.append(line).append("\n");

                // 检查标题后是否有内容，如果有，将标题与后续内容合并
                boolean nextLineHasContent = false;
                for (int i = blocks.size(); i < lines.length; i++) {
                    if (!lines[i].startsWith("#") && !lines[i].trim().isEmpty()) {
                        nextLineHasContent = true;
                        break;
                    }
                }

                // 如果没有下文，则继续合并标题到下一块
                if (!nextLineHasContent) {
                    blocks.add(currentBlock.toString().trim()); // 如果没有下文内容，标题和空行加入一个块
                    currentBlock.setLength(0); // 清空当前块
                }
            } else {
                // 处理正常文本内容
                if (currentBlock.length() + line.length() > maxWords) {
                    blocks.add(currentBlock.toString().trim()); // 如果超过 maxWords，分割
                    currentBlock.setLength(0); // 清空当前块
                }
                currentBlock.append(line).append(" ");
            }
        }

        if (currentBlock.length() > 0) {
            blocks.add(currentBlock.toString().trim()); // 加入最后一个块
        }

        return blocks;
    }

}
