package io.github.zxh111222.sbblog.service;

import io.github.zxh111222.sbblog.entity.PasswordResetToken;

public interface PasswordResetTokenService {
    PasswordResetToken findFirstByToken(String token);

    PasswordResetToken save(PasswordResetToken passwordResetToken);
}