package com.qianfeng.v13serachweb.rabbitmq;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qianfeng.serarch.ISerarchService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author wwn
 * @Date 2019/6/21
 */
@Component
public class ComsumerExcuse {

    @Reference
    private ISerarchService serarchService;

    @RabbitHandler
    @RabbitListener(queues = "solr")
    public void  solrAdd(Long id){
        serarchService.synById(id);
    }
}
