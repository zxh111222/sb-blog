package io.github.zxh111222.sbblog.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private LocalDateTime createdAt;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles = new HashSet<>();
}
