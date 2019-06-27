package com.qianfeng.v13serachweb.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author wwn
 * @Date 2019/6/21
 */
@Configuration
public class RabbitmqConsumer {
    //声明交换机和队列
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange("product_solr_exchange");
    }

    @Bean
    public Queue getQueueSolr(){
        return  new Queue("solr",true,false,false);
    }

    //绑定队列到交换机上
    @Bean
    public Binding bindingExchangeSolr(Queue getQueueSolr, TopicExchange getTopicExchange){
        return BindingBuilder.bind(getQueueSolr).to(getTopicExchange).with("add");
    }
}
