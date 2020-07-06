package com.example.controller;


import com.example.bean.Role;
import com.example.bean.Role;
import com.example.serivce.RoleService;
import com.example.serivce.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/find")
    public List<Role> find(){
        List<Role> list = roleService.list();
        return list;
    }

    @GetMapping("/get")
    public Role get(Role Role){
        Role u = roleService.getById(Role.getId());
        return u;
    }

    @GetMapping("/add")
    public String add(Role Role){
        boolean save = roleService.save(Role);
        return "SAVE SUCCESS";
    }

    @GetMapping("/edit")
    public String edit(Role Role){
        boolean edit = roleService.updateById(Role);
        return "EDIT SUCCESS";
    }

    @GetMapping("/delete")
    public String delete(Role Role){
        boolean del = roleService.removeById(Role.getId());
        return "DEL SUCCESS";
    }
}
