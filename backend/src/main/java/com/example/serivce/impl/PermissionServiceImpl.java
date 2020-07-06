package com.example.serivce.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bean.Permission;
import com.example.mapper.PermissionMapper;
import com.example.serivce.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> findPermissionsByUserId(String userId) {
        return permissionMapper.findPermissionsByUserId(userId);
    }
}
