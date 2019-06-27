package com.qianfeng.v13itemweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author wwn
 * @Date 2019/6/20
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor getPool(){
        //cpu核数
        int cpus = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(cpus, cpus*2, 10, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        return  pool;
    }
}
