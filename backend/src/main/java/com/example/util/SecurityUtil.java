package com.example.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bean.User;
import com.example.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SecurityUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private UserService userService;

    /**
     * 获取当前登录用户信息
     * @return
     * todo 后期优化
     */
    public User getLoginUser(){
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_name",user.getUsername());
        return userService.getOne(queryWrapper);
    }

}
