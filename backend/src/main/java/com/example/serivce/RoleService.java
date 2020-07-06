package com.example.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Role;
import com.example.bean.User;

import java.util.List;


public interface RoleService extends IService<Role> {

    //通过用户ID查询所有角色
    List<Role> findRolesByUserId(String userId);

}
