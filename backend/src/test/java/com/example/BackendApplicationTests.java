package com.example;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.bean.User;
import com.example.serivce.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SpringBootTest
public class BackendApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(  "user_name", "zhang");
        List<User> users = userService.list(wrapper);
        System.out.println(users);
        users.forEach(u->{
            System.out.println(u);
        });
    }

}
