package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    /**
     * 如果自动跳转到这个页面，说明用户未登录，返回相应的提示即可
     *  
     *    如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面
     *
     * @return 调用登录接口，未登录，走这个方法   http://localhost:8083/login
     *   
     */
    @RequestMapping("/login_page")
    public Map loginPage() {
        System.out.println("=========error===========");
        Map map = new HashMap();
        map.put("status", "400");
        map.put("msg", "尚未登录，请登录!");
        return map;
    }

}
