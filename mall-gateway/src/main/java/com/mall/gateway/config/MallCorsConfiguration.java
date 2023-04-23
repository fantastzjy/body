package com.mall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @Description: 解决跨域的配置
 **/

@Configuration
public class MallCorsConfiguration {

    //要加入到容器中
    @Bean
    public CorsWebFilter corsWebFilter() {
        //因为网关都是响应式的，所以使用响应式包reactive下面的类
        //过滤条件
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //1、配置跨域
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        //是否允许携带cookie进行跨域,否则会丢失相关的cookie信息
        corsConfiguration.setAllowCredentials(true);

        //设置所有请求都允许跨域
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
