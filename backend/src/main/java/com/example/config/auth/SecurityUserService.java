package com.example.config.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.Permission;
import com.example.bean.Role;
import com.example.bean.User;
import com.example.serivce.PermissionService;
import com.example.serivce.RoleService;
import com.example.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现UserDetailsService接口
 * 使用@Component把它注册为组件，便于spring进行实例化
 */
@Component
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    //根据返回的这个带有正确用户信息的对象和前台传过来
    // 的用户名密码进行比对来判断是否认证通过
    //根据用户名去查找用户，如果用户不存在，
    // 则抛出UsernameNotFoundException异
    //点击登录调用
    // 我们实现动态加载用户、角色、权限信息就是通过实现该方法。函数见名知义：通过用户名加载用户。该方法的返回值就是UserDetails。
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        User user = userService.getOne(wrapper);// 用户名必须唯一
        if (user == null) {
            throw new UsernameNotFoundException("Username :" + username + "not found");
        }
        //查询用户角色
        List<Role> roles = roleService.findRolesByUserId(user.getId());
        //设置角色
        user.setRoles(roles);
        //查询用户权限
        List<Permission> permissions = permissionService.findPermissionsByUserId(user.getId());
        //设置权限
        user.setPermissions(permissions);
        SecurityUser securityUser = new SecurityUser(user);
        return securityUser;
    }
}
