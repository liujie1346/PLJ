package com.example.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolePermission implements Serializable {
    private static final long serialVersionUID = -6162401270776653321L;
    private String resource_url;
    private String role_name;
}
