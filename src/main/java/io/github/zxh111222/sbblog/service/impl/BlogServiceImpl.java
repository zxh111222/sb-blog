package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.entity.Blog;
import io.github.zxh111222.sbblog.dto.BlogDTO;
import io.github.zxh111222.sbblog.dao.BlogRepository;
import io.github.zxh111222.sbblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    BlogRepository blogRepository;

    @Override
    public Blog save(BlogDTO blogDTO) {
        // 1. 手动把 BlogDTO 转化为 Blog (之后会有自动的方案)
        Blog blog = new Blog();

        if (blogDTO.getId() != null) {
            blog = blogRepository.findById(blogDTO.getId()).get();
            blog.setUpdated_at(LocalDateTime.now());
        } else {
            blog.setCreated_at(LocalDateTime.now());
        }

        blog.setTitle(blogDTO.getTitle());
        blog.setContent(blogDTO.getContent());
        blog.setDescription(blogDTO.getDescription());
        blog.setCover(blogDTO.getCover());
        // 2. 存数据库
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> findByTitleContaining(String keyword, Pageable pageable) {
        return blogRepository.findByTitleContaining(keyword, pageable);
    }

    @Override
    public void deleteById(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Optional<Blog> findById(Long id) {
        return blogRepository.findById(id);
    }
}
