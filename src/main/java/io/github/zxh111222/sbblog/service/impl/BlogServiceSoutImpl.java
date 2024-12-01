package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.Blog;
import io.github.zxh111222.sbblog.BlogDTO;
import io.github.zxh111222.sbblog.BlogRepository;
import io.github.zxh111222.sbblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class BlogServiceSoutImpl implements BlogService {

    @Override
    public Blog save(BlogDTO blog) {
        System.out.println(blog);
        return null;
    }
}
