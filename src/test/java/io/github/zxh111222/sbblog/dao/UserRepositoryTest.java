package io.github.zxh111222.sbblog.dao;

import io.github.zxh111222.sbblog.entity.Permission;
import io.github.zxh111222.sbblog.entity.Role;
import io.github.zxh111222.sbblog.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void findUserByName(){
        User user = new User();
        String userName = "test";
        user.setName(userName);
        user.setPassword(passwordEncoder.encode("password"));
        user.setEnabled(true);

        userRepository.save(user);

        Optional<User> optionalUser = userRepository.findFirstByName(userName);
        Assertions.assertTrue(optionalUser.isPresent());
    }

    @Test
    void rbac(){
        // admin 用户具有 admin 角色
        // admin角色具有两个权限
        String roleName = "admin";
        Optional<User> ou = userRepository.findFirstByName(roleName);
        Assertions.assertTrue(ou.isPresent());

        User user = ou.get();
        Set<Role> roles = user.getRoles();
        int actual = roles.size();
        Assertions.assertEquals(1, actual);

        Role role = roles.iterator().next();
        String actualName = role.getName();
        Assertions.assertEquals(roleName, actualName);

        // 测试角色跟权限的关系
        Set<Permission> permissions = role.getPermissions();
        Assertions.assertEquals(3, permissions.size());

        Set<String> permissionNames = new HashSet<>();
        for (Permission permission : permissions) {
            permissionNames.add(permission.getName());
        }
        Assertions.assertTrue(permissionNames.contains("/backend"));
        Assertions.assertTrue(permissionNames.contains("/backend/blog"));
        Assertions.assertTrue(permissionNames.contains("/backend/user"));
    }

}
