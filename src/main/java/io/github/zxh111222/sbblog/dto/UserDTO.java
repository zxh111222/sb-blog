package io.github.zxh111222.sbblog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserDTO {
    Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 15, message = "用户名长度应该在3到15之间")
    String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, message = "密码不少于6位")
    String password;

    @NotBlank(message = "确认密码不能为空")
    String checkPassword;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入有效的邮箱地址")
    String email;

    String mobile;

    Boolean enabled;

    LocalDateTime createdAt;

}
