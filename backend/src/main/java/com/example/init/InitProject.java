package com.example.init;


import com.alibaba.fastjson.JSON;
import com.example.bean.RolePermission;
import com.example.serivce.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)   //通过order值的大小来决定启动的顺序
public class InitProject implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("项目启动...");

        //初始化角色权限
        List<RolePermission> rolePermissions = rolePermissionService.findAllRolePermissions();
        redisTemplate.opsForValue().set("ROLE_PERMISSIONS" , JSON.toJSON(rolePermissions).toString());


    }

}
