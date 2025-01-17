package io.github.zxh111222.sbblog.dto;


import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PasswordResetEmailDTO {

    @Email
    String email;
}
