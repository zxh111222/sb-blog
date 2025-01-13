package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.dao.UserRepository;
import io.github.zxh111222.sbblog.entity.User;
import io.github.zxh111222.sbblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void save() {
        System.out.println("UserServiceImpl.save");;
        User user = new User();
        user.setName("name_" + System.currentTimeMillis());
        user.setEmail(System.currentTimeMillis() + "@example.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
