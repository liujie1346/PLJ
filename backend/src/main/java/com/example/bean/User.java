package com.example.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@TableName(value = "sys_user")//指定表名
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4954856044245595470L;
    private String id;
    private String user_name;
    private String password;
    private String description;
    private Integer state;
    //@TableField(exist = false)//非数据库字段
    //private Set<Role> roles = new LinkedHashSet<>();
    @TableField(exist = false)//非数据库字段
    private List<Role> roles;
    @TableField(exist = false)//非数据库字段
    private List<Permission> permissions;

}
