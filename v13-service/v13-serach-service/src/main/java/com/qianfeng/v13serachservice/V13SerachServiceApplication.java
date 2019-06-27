package com.qianfeng.v13serachservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qianfeng.v13.mapper")
@EnableDubbo//开启开关
public class V13SerachServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V13SerachServiceApplication.class, args);
    }

}
