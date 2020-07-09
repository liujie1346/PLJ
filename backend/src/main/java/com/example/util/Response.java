package com.example.util;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Response implements Serializable {

    private static final long serialVersionUID = 2633209103779898431L;

    //todo 后期优化
    public Response(HttpServletResponse response,
                    String code, String err, String msg, Object data) {
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("code", code);
        result.put("msg", msg);
        result.put("err", err);
        if (data != null) {
            result.put("data", data);
        }
        result(response, result);
    }

    //封装结果
    public void result(HttpServletResponse response, Map<String, Object> result) {
        ServletOutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out = response.getOutputStream();
            out.write(JSON.toJSONString(result).getBytes());
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}