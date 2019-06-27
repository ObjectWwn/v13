package com.qianfeng.v13commonservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class V13CommonServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(V13CommonServiceApplication.class, args);
    }

}
