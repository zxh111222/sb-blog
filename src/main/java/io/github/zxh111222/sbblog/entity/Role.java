package io.github.zxh111222.sbblog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Role {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    int sort;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permissions_id", referencedColumnName = "id")
    )
    Set<Permission> permissions = new HashSet<>();
}
