package io.github.zxh111222.sbblog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @Email
    String email;

    @Size(min = 3, max = 20, message = "用户名长度必须在 3-20 之间")
    String name;

    @Size(min = 2, max = 12)
    String password;

}
