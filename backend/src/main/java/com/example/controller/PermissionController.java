package com.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.Permission;
import com.example.serivce.PermissionService;
import com.example.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/permission")
@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @GetMapping("/find")
    public Result find() {
        Result result = new Result();
        List<Permission> list = permissionService.list();
        JSONArray arr = new JSONArray();
        for (Permission permission : list) {
            if ("0".equals(permission.getParent_id())) {
                JSONObject obj = new JSONObject();
                obj.put("id",permission.getId());
                obj.put("resource_name",permission.getResource_name());
                obj.put("resource_method",permission.getResource_method());
                obj.put("resource_url",permission.getResource_url());
                obj.put("parent_id",permission.getParent_id());
                obj.put("sort",permission.getSort());
                obj.put("description",permission.getDescription());
                JSONArray arr2 = new JSONArray();
                for (Permission permission2 : list) {
                    if(permission.getId().equals(permission2.getParent_id())){
                        JSONObject obj2 = new JSONObject();
                        obj2.put("id",permission2.getId());
                        obj2.put("resource_name",permission2.getResource_name());
                        obj2.put("resource_method",permission2.getResource_method());
                        obj2.put("resource_url",permission2.getResource_url());
                        obj2.put("parent_id",permission2.getParent_id());
                        obj2.put("sort",permission2.getSort());
                        obj2.put("description",permission2.getDescription());
                        arr2.add(obj2);
                    }
                }
                obj.put("children",arr2);
                arr.add(obj);
            }
        }
        result.setCode("200");
        result.setMsg("操作成功");
        result.setData(arr);
        return result;
    }

    @GetMapping("/get")
    public Permission get(Permission permission) {
        Permission u = permissionService.getById(permission.getId());
        return u;
    }

    @GetMapping("/add")
    public String add(Permission permission) {
        boolean save = permissionService.save(permission);
        return "SAVE SUCCESS";
    }

    @GetMapping("/edit")
    public String edit(Permission permission) {
        boolean edit = permissionService.updateById(permission);
        return "EDIT SUCCESS";
    }

    @GetMapping("/delete")
    public String delete(Permission permission) {
        boolean del = permissionService.removeById(permission.getId());
        return "DEL SUCCESS";
    }

}
