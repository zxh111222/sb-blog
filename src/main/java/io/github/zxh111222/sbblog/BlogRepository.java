package io.github.zxh111222.sbblog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // 根据标题模糊查询
    List<Blog> findByTitleContaining(String title);

}
