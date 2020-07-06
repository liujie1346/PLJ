package com.example.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.Role;
import com.example.mapper.RoleMapper;
import com.example.serivce.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRolesByUserId(String userId) {
        return roleMapper.findRolesByUserId(userId);
    }
}
