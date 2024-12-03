package io.github.zxh111222.sbblog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlogDTO {
    Long id;

    @NotEmpty
    @Size(min = 6)
    String title;

    @NotEmpty
    @Size(min = 25)
    String content;

    String cover;

    @Size(max = 100)
    String description;
}
