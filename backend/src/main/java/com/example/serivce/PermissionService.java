package com.example.serivce;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bean.Permission;

import java.util.List;


public interface PermissionService extends IService<Permission> {

    List<Permission> findPermissionsByUserId(String userId);

}
