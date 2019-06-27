package com.qianfeng.v13itemweb;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ItemWebApplicationTests {

    @Autowired
    private Configuration configuration;

    @Test
    public void createHTMLTest() {
        try {
            //1.获取模板对象
            Template template = configuration.getTemplate("hello.ftl");
            //2.设置模板数据
            Map<String,Object> data = new HashMap<>();
            data.put("name","zhangshan");
            //3.模板+数据，最终生成静态页面
            FileWriter writer = new FileWriter("D:\\idea(WorkSpace)\\v13\\v13-web\\v13-item-web\\src\\main\\resources\\templates\\hello.html");
            template.process(data,writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        System.out.println("生成静态页面成功！");
    }

}
