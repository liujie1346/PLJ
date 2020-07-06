package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.RolePermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    //查询所有角色对应的权限
    @Select("SELECT " +
            " p.resource_url, " +
            " r.role_name " +
            "FROM sys_permission p " +
            "LEFT JOIN sys_role_permission rp ON p.id=rp.permission_id " +
            "LEFT JOIN sys_role r ON rp.role_id=r.id")
    List<RolePermission> findAllRolePermissions();

}
