package com.example.config.auth;

import com.alibaba.fastjson.JSON;
import com.example.bean.User;
import com.example.util.Response;
import com.example.util.SecurityUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@EnableGlobalMethodSecurity(prePostEnabled = true)//开启基于方法的安全认证机制，也就是说在web层的controller启用注解机制的安全确认
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //token分割
    final String TOKEN_SPLIT = "Bearer ";
    //JWT签名加密key
    final String JWT_SIGN_KEY = "pangliujie";
    //token参数头
    final String HEADER = "accessToken";
    //权限参数头
    final String AUTHORITIES = "authorities";
    //权限参数头
    final int EXPIRE_TIME = 30;
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico","/verifyCode");
    }


    //使用jwt
    @Bean
    public JWTAuthenticationFilter authenticationTokenFilterBean()  {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        // 开启允许iframe 嵌套
        registry.and().headers().frameOptions().disable();

        // 忽略路径,多个","分割
                // 对登录注册要允许匿名访问;
        registry.antMatchers("/captcha/init",
                        "/captcha/draw/**",
                        "/login_page"
                ).permitAll();


        System.out.println("放过路径。。。");


        registry.and()
                .csrf() //跨站
                .disable() //关闭跨站检CreateVerifyCode测
                .cors() // 开启跨域


                .and().formLogin()
                .loginPage("/login_page")//登录页面的地址
                //这里配置的loginProcessingUrl为页面中对应表单的 action ，该请求为 post，并设置可匿名访问
                .loginProcessingUrl("/login")
                .permitAll()//放行/login接口
                //登录成功后的返回结果
                .successHandler(new AuthenticationSuccessHandlerImpl())
                //登录失败后的返回结果
                .failureHandler(new AuthenticationFailureHandlerImpl())
                //这里配置的logoutUrl为登出接口，并设置可匿名访问
                .and().logout().permitAll()
                //清除认证信息
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)

                //登出后的返回结果
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
//                .invalidateHttpSession(true)  //定义登出时是否invalidate HttpSession，默认为true

                // 其余所有请求都需要认证
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()

                //这里配置的为当未登录访问受保护资源时，返回json，并且让springsecurity自带的登录界面失效
                .and().exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPointHandler())
                .accessDeniedHandler(new CustomAccessDeineHandler())
                .and()
                /**
                 * session策略，此处选用禁用session
                 *  ALWAYS:总是创建HttpSession
                 *  IF_REQUIRED:Spring Security只会在需要时创建一个HttpSession
                 *  NEVER:Spring Security不会创建HttpSession，但如果它已经存在，将可以使用HttpSession
                 *  STATELESS:Spring Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
                 */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        ;

        // 添加JWT filter
        registry.and().addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        // url权限认证处理
        registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        });

        // 记住我
//        http.rememberMe().rememberMeParameter("remember-me")
//                .userDetailsService(securityUserService).tokenValiditySeconds(1000);
    }



    //授权
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //通过Jasypt对用户密码进行加密
        auth.userDetailsService(securityUserService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //定义登陆成功返回信息
    private class AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
        /*
           request：相当与HttpServletRequest
           response：相当与HttpServletRespose
           authentication：这里保存了我们登录后的用户信息
         */
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            String userId = ((User)authentication.getPrincipal()).getId();
            System.out.println(userId+"禁用seesion==="+username);
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
            List<String> list = new ArrayList<>();
            for(GrantedAuthority g : authorities){
                list.add(g.getAuthority());
            }

//            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            // 全局注入角色权限信息和登录用户基本信息
//            SecurityContextHolder.getContext().setAuthentication(auth);

            //登陆成功，将用户角色保存到reids中,30分钟
            /**
             *   TimeUnit.DAYS          //天
             *   TimeUnit.HOURS         //小时
             *   TimeUnit.MINUTES       //分钟
             *   TimeUnit.SECONDS       //秒
             *   TimeUnit.MILLISECONDS  //毫秒
             */
//            redisTemplate.opsForValue().set("AUTH:ROLE_"+userId,JSON.toJSONString(authorities), 30,TimeUnit.MINUTES );
            // jwt
            String token = TOKEN_SPLIT + Jwts.builder()
                    //主题 放入用户名
                    .setSubject(username)
                    //自定义属性 放入用户拥有请求权限
                    .claim(AUTHORITIES, JSON.toJSON(list).toString())
                    //失效时间
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME * 60 * 1000))
                    //签名算法和密钥
                    .signWith(SignatureAlgorithm.HS512, JWT_SIGN_KEY)
                    .compact();
            Map map = new HashMap<>();
            map.put(HEADER,token);
            new Response(response,
                    "200",
                    "登录成功",
                    "登录成功",
                    map
            );
        }
    }

    //定义登出成功返回信息
    private class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler {
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException{

            System.out.println("logout-----authentication---"+authentication);
//            UserDetails user = ((UserDetails)authentication.getPrincipal());

            //登陆成功，将用户角色保存到reids中,30分钟
//            redisTemplate.delete("AUTH:ROLE_"+userId);
            new Response(response,
                    "200",
                    "登出成功",
                    "登出成功",
                    null
            );
        }
    }

    //定义登陆失败返回信息
    private class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {
        /*   request：相当与HttpServletRequest
             response：相当与HttpServletRespose
             e：这里保存了我们登录失败的原因
        */
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
//            response.setContentType("application/json;charset=utf-8");
            String rm = "";
            if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
                rm = "账户名或者密码输入错误!";
            } else if (e instanceof LockedException) {
                rm = "账户被锁定，请联系管理员!";
            } else if (e instanceof CredentialsExpiredException) {
                rm = "密码过期，请联系管理员!";
            } else if (e instanceof AccountExpiredException) {
                rm = "账户过期，请联系管理员!";
            } else if (e instanceof DisabledException) {
                rm = "账户被禁用，请联系管理员!";
            } else {
                rm = "登录失败!";
            }
            new Response(response,
                    "400",
                    rm,
                    rm,
                    null
            );
        }
    }

    //AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
    public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException  {
            new Response(response,
                    "400",
                    "未登录用户无权限访问资源1",
                    "未登录用户无权限访问资源1",
                    null
            );
        }
    }

    //AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
    public class CustomAccessDeineHandler implements AccessDeniedHandler {
        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
            new Response(response,
                    "400",
                    "已登录用户无权限访问资源2",
                    "已登录用户无权限访问资源2",
                    null
            );
        }
    }

}
