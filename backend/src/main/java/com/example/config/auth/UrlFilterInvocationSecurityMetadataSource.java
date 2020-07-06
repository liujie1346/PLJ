package com.example.config.auth;

import com.alibaba.fastjson.JSON;
import com.example.bean.Permission;
import com.example.bean.RolePermission;
import com.example.serivce.PermissionService;
import com.example.serivce.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;


//自定义UrlFilterInvocationSecurityMetadataSource实现FilterInvocationSecurityMetadataSource重写getAttributes()方法 获取访问该url所需要的角色权限信息
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    /***
     * 返回该url所需要的用户权限信息
     *
     * @param o: 储存请求url信息
     * @return: null：标识不需要任何权限都可以访问
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        // 获取当前请求url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        // TODO 忽略url请放在此处进行过滤放行
        // "/login"在WebSecurityConfig.java中已经放行
        if ("/login_page".equals(requestUrl) ||
                "/captcha/init".equals(requestUrl) ||
                requestUrl.contains("/captcha/draw") ||
                requestUrl.contains("logout")) {
            return null;
        }
        // 缓存中所有url，与当前url比对，返回角色
        String auth = redisTemplate.opsForValue().get("ROLE_PERMISSIONS");
        List<RolePermission> rolePermissions = JSON.parseArray(auth, RolePermission.class);
        for (RolePermission rp : rolePermissions) {
            // 获取该url所对应的权限
            if (requestUrl.equals(rp.getResource_url())) {
                String roleName = rp.getRole_name();//角色名称
                // 保存该url对应角色权限信息
                return SecurityConfig.createList(roleName);//创建一个角色集合
            }
        }
        // 如果数据中没有找到相应url资源则为非法访问，要求用户登录再进行操作
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
