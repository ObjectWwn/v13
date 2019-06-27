package com.qianfeng.v13.centweb.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wwn
 * @Date 2019/6/21
 */
@Configuration
public class RabbitmqProduct {
    //声明交换机
    @Bean
    public TopicExchange getFanoutExchange(){
        return new TopicExchange("product_solr_exchange");
    }
}
