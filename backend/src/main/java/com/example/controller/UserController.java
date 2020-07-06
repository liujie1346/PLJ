package com.example.controller;

import com.example.bean.User;
import com.example.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/find")
    public List<User> find(){
        List<User> list = userService.list();
        return list;
    }

    @GetMapping("/get")
    public User get(User user){
        User u = userService.getById(user.getId());
        return u;
    }

    @GetMapping("/add")
    public String add(User user){
        boolean save = userService.save(user);
        return "SAVE SUCCESS";
    }

    @GetMapping("/edit")
    public String edit(User user){
        boolean edit = userService.updateById(user);
        return "EDIT SUCCESS";
    }

    @GetMapping("/delete")
    public String delete(User user){
        boolean del = userService.removeById(user.getId());
        return "DEL SUCCESS";
    }

    @GetMapping("/user")
    public UserDetails user(HttpServletRequest request){
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(request+"o==============="+o);
        UserDetails user = (UserDetails) o;
        return user;
    }


}
