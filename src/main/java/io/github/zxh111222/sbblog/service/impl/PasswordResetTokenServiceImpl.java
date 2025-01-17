package io.github.zxh111222.sbblog.service.impl;

import io.github.zxh111222.sbblog.dao.PasswordResetTokenRepository;
import io.github.zxh111222.sbblog.entity.PasswordResetToken;
import io.github.zxh111222.sbblog.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordResetToken findFirstByToken(String token) {
        return passwordResetTokenRepository.findFirstByToken(token).orElse(null);
    }

    @Override
    public PasswordResetToken save(PasswordResetToken passwordResetToken) {
        return passwordResetTokenRepository.save(passwordResetToken);
    }
}