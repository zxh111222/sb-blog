package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.dao.UserRepository;
import io.github.zxh111222.sbblog.dto.UserDTO;
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
    public void save(UserDTO userDTO) {
        System.out.println("UserServiceImpl.save");
        String username = userDTO.getName();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String checkPassword = userDTO.getPassword();

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
