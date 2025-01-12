package io.github.zxh111222.sbblog.bean;

import lombok.Getter;
import io.github.zxh111222.sbblog.entity.Permission;
import io.github.zxh111222.sbblog.entity.Role;
import io.github.zxh111222.sbblog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class SecurityUser implements UserDetails {
    @Getter
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> permissions = new ArrayList<>();
        Set<Role> roles = this.user.getRoles();
        for (Role role : roles) {
            permissions.add("ROLE_" + role.getName());
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getName());
            }
        }
        return AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", permissions));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getName();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}