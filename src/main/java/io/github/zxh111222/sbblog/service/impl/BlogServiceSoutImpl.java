package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.Blog;
import io.github.zxh111222.sbblog.BlogDTO;
import io.github.zxh111222.sbblog.service.BlogService;

public class BlogServiceSoutImpl implements BlogService {
    @Override
    public Blog save(BlogDTO blog) {
        System.out.println(blog);
        return null;
    }
}
