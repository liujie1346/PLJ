package com.example.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_role")//指定表名
public class Role implements Serializable {
    private static final long serialVersionUID = -7374019357395031208L;
    private String id;
    private String role_name;
    private String description;
}
