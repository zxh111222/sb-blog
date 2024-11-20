package io.github.zxh111222.sbblog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // 按标题模糊查询并实现分页
    Page<Blog> findByTitleContaining(String title, Pageable pageable);

}
