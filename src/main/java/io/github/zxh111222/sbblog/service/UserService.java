package io.github.zxh111222.sbblog.service;


import io.github.zxh111222.sbblog.entity.User;
import jakarta.validation.constraints.Email;

public interface UserService {
    void save();

    User findByEmail(@Email String email);
}
