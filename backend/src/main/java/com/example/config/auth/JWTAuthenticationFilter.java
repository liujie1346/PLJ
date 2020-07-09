package com.example.config.auth;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.util.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    //token分割
    final String TOKEN_SPLIT = "Bearer ";
    //JWT签名加密key
    final String JWT_SIGN_KEY = "pangliujie";
    //token参数头
    final String HEADER = "accessToken";
    //权限参数头
    final String AUTHORITIES = "authorities";
//    private SecurityUtil securityUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("过滤器。。。。。1。。。。。。。");
        String header = request.getHeader(HEADER);
        System.out.println("header ====" + header);
        if (StringUtils.isBlank(header)) {
            header = request.getParameter(HEADER);
        }
        Boolean notValid = StringUtils.isBlank(header) || !header.startsWith(TOKEN_SPLIT);

        if (notValid) {
            System.out.println("token信息不存在，或错误............");
            chain.doFilter(request, response);
            return;
        }

        //一下路径 ，含有accessToken也要放过
        String uri = request.getRequestURI();
        if ("/login".equals(uri) ||"/captcha/init".equals(uri) ||
                uri.contains("/captcha/draw") ) {
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.toString();
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

        // 用户名
        String username = null;
        // 权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // JWT
        try {
            // 解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SIGN_KEY)
                    .parseClaimsJws(header.replace(TOKEN_SPLIT, ""))
                    .getBody();

            // 获取用户名
            username = claims.getSubject();
            // 获取权限
            String authority = claims.get(AUTHORITIES).toString();
            if (StringUtils.isNotBlank(authority)) {
                List<String> list = JSON.parseArray(authority, String.class);
                for (String ga : list) {
                    // 权限
                    authorities.add(new SimpleGrantedAuthority(ga));
                }
            }
        } catch (ExpiredJwtException e) {
            new Response(response,
                    "401",
                    "登录已失效，请重新登录",
                    "登录已失效，请重新登录",
                    null
            );
        } catch (Exception e) {
            response.setContentType("application/json;charset=utf-8");
            new Response(response,
                    "500",
                    "解析token错误",
                    "解析token错误",
                    null
            );
        }

        if (StringUtils.isNotBlank(username)) {
            // 踩坑提醒 此处password不能为null
            User principal = new User(username, "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, null, authorities);
        }
        return null;
    }
}

