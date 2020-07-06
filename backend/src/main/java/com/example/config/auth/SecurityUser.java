package com.example.config.auth;

import com.example.bean.Role;
import com.example.bean.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  自定义User类实现UserDetails
 * */
public class SecurityUser extends User implements UserDetails{

    //构造方法
    public SecurityUser(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setUser_name(user.getUser_name());
            this.setPassword(user.getPassword());
            this.setDescription(user.getDescription());
            this.setState(user.getState());
            // 角色
            this.setRoles(user.getRoles());
            // 权限
            this.setPermissions(user.getPermissions());
        }
    }

    // 获取用户的权限集合：getAuthorities()获取当前用户所具有的角色
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        List<Role> roles = super.getRoles();
        if(roles != null){
            for(Role role : roles){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole_name());
                authorities.add(authority);
            }
        }
        return authorities;
    }

    //获取用户名
    @Override
    public String getUsername() {
        return this.getUser_name();
    }
    //账号是否没过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //账号是否没被锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //密码是否没过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
