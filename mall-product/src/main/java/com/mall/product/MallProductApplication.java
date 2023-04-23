package com.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@EnableRedisHttpSession   // springsession共享
@EnableCaching      //开启缓存功能
@EnableFeignClients(basePackages = "com.mall.product.feign")
@EnableDiscoveryClient
@MapperScan("com.mall.product.dao")
@SpringBootApplication //(exclude = GlobalTransactionAutoConfiguration.class)
public class MallProductApplication {

    public static void main(String[] args) {

        SpringApplication.run(MallProductApplication.class, args);
    }

}


