package io.github.zxh111222.sbblog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogDTO {
    Long id;
    String title;
    String content;
    String cover;
    String description;
}
