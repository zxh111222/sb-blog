package io.github.zxh111222.sbblog.dao;

import io.github.zxh111222.sbblog.entity.Role;
import io.github.zxh111222.sbblog.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    }

}
