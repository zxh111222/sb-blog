package io.github.zxh111222.sbblog.entity;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

    private LocalDateTime created_at;

    @Nullable
    private LocalDateTime updated_at;

}
