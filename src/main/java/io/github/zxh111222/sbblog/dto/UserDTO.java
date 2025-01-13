package io.github.zxh111222.sbblog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {
    Long id;

    String name;

    String password;

    String email;

    String mobile;

    Boolean enabled;

    Date createTime;

}
