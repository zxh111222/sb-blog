package io.github.zxh111222.sbblog.service;

import io.github.zxh111222.sbblog.entity.Blog;
import io.github.zxh111222.sbblog.dto.BlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BlogService {
    public Blog save(BlogDTO blog);

    Page<Blog> findAll(Pageable pageable);

    Page<Blog> findByTitleContaining(String keyword, Pageable pageable);

    void deleteById(Long id);

    Optional<Blog> findById(Long id);
}
