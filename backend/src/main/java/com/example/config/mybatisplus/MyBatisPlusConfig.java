package com.example.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement//开启事务支持
@Slf4j
@Configuration
public class MyBatisPlusConfig {

    //配置分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        log.info("注册分页插件");
        return new PaginationInterceptor();
    }


    //SQL执行效率插件【生产环境可以关闭】
//    @Bean
//    @Profile({"test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

    // 逻辑删除用，3.1.1之后的版本可不需要配置该bean
//    @Bean
//    public ISqlInjector sqlInjector() {
//        return new LogicSqlInjector();
//    }
}
