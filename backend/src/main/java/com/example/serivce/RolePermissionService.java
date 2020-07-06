package com.example.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Role;
import com.example.bean.RolePermission;

import java.util.List;


public interface RolePermissionService extends IService<RolePermission> {

    List<RolePermission> findAllRolePermissions();

}
