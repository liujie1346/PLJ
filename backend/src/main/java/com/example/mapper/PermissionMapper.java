package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {

    //通过用户ID查询所有权限
    @Select("SELECT  " +
            " p.id, " +
            " p.resource_name, " +
            " p.resource_method, " +
            " p.resource_url, " +
            " p.parent_id, " +
            " p.sort, " +
            " p.description " +
            "FROM sys_role_user ru   " +
            "LEFT JOIN sys_role_permission rp ON ru.role_id=rp.role_id  " +
            "LEFT JOIN sys_permission p ON rp.permission_id=p.id " +
            "WHERE ru.user_id=#{userId} " +
            "ORDER BY sort ASC")
    List<Permission> findPermissionsByUserId(String userId);
}
