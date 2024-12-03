package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.entity.Blog;
import io.github.zxh111222.sbblog.dto.BlogDTO;
import io.github.zxh111222.sbblog.service.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

//@Service
public class BlogServiceSoutImpl implements BlogService {

    @Override
    public Blog save(BlogDTO blog) {
        System.out.println(blog);
        return null;
    }

    @Override
    public Page<Blog> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Blog> findByTitleContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public Optional<Blog> findById(Long id) {
        return Optional.empty();
    }
}
