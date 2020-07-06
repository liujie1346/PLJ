package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bean.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    //通过用户ID查询所有角色
    @Select("SELECT  " +
            " r.id, " +
            " r.role_name, " +
            " r.description " +
            "FROM sys_role_user ru " +
            "LEFT JOIN sys_role r ON r.id=ru.role_id " +
            "WHERE ru.user_id=#{userId}")
    List<Role> findRolesByUserId(String userId);

}
