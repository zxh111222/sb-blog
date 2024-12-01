package io.github.zxh111222.sbblog;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.DialectOverride;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String cover;

    @Nullable
    private String description;

    public Blog() {
    }

    public Blog(String title, String content, String cover) {
        this.title = title;
        this.content = content;
        this.cover = cover;
    }

    public Blog(Long id, String title, String content, String cover) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.cover = cover;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
