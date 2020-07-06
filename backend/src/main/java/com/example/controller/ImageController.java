package com.example.controller;

import com.example.util.VCodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.util.VCodeUtil.outputImage;

@RestController
@RequestMapping("/captcha")
public class ImageController {

    @GetMapping("/init")
    public Map<String, Object> initCaptcha() {
        String captchaId = UUID.randomUUID().toString().replace("-", "");
        Map<String, Object> map = new HashMap<>();
        map.put("captchaId", captchaId);
        map.put("status", "200");
        return map;
    }

    @GetMapping("/draw/{captchaId}")
    public void getImg(HttpServletResponse response,@PathVariable String captchaId) throws IOException {
        System.out.println("==========captchaId=========="+captchaId);
        /**
         *  获取验证码
         */
        String verifyCode = VCodeUtil.generateVerifyCode(4);//验证码字符个数
        System.out.println(verifyCode);
        int w = 100, h = 30;
        outputImage(w, h, response.getOutputStream(), verifyCode);
    }


}
