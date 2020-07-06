package com.example.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_permission")//指定表名
public class Permission implements Serializable {
    private static final long serialVersionUID = -6162401270776653321L;
    private String id;
    private String resource_name;
    private String resource_method;
    private String resource_url;
    private String parent_id;
    private String sort;
    private String description;
}
