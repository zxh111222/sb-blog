package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.common.ErrorCode;
import io.github.zxh111222.sbblog.dao.UserRepository;
import io.github.zxh111222.sbblog.dto.UserDTO;
import io.github.zxh111222.sbblog.entity.User;
import io.github.zxh111222.sbblog.exception.BusinessException;
import io.github.zxh111222.sbblog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "xinh";

    @Override
    public void save(UserDTO userDTO) {
        String username = userDTO.getName();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String checkPassword = userDTO.getCheckPassword();

        // 校验
        // 非空 - 不用再验证
//        if (StringUtils.isAnyBlank(username, email, password, checkPassword)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
//        }
        // 账户长度3-15位之间 - 不用再验证
        // 密码不小于6位 - 不用再验证
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(username);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名包含特殊字符");
        }
        // 密码和校验密码相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "校验密码与原密码不相同");
        }
        // 邮箱不能重复
        Optional<User> existingUser = userRepository.findFirstByEmail(email);
        if (existingUser.isPresent()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该邮箱已存在");
        }

        User user = new User();
        user.setName(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(SALT + password));
        user.setEnabled(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

    }
}
