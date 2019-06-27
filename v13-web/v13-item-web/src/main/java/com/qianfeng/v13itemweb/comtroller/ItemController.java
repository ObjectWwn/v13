package com.qianfeng.v13itemweb.comtroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.v13.api.IProductService;
import com.qianfeng.v13.entity.TProduct;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author wwn
 * @Date 2019/6/18
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private Configuration configuration;

    @Reference
    private IProductService productService;

    //用单例模式防止线程池被多次创建
    @Autowired
    private  ThreadPoolExecutor pool;

    @RequestMapping("createHTML/{id}")
    @ResponseBody
    public ResultBean createHTML(@PathVariable("id") Long id){
        return createHTMLByID(id, ".html", "生成静态页成功");
    }

    @RequestMapping("batchCreateHTML")
    public  ResultBean batchCreateHTML(@RequestParam List<Long> ids){

        for (Long id : ids) {
            pool.submit(new CreateHtmlTask(id));
        }
        return new ResultBean("200","批量创建成功");
    }

    class CreateHtmlTask implements Callable<ResultBean> {

        private Long id;

        public CreateHtmlTask(Long id){
            this.id = id;
        }

        @Override
        public ResultBean call() throws Exception {
            //1.获取商品信息
            TProduct tProduct = productService.selectByPrimaryKey(id);
            try {
                //2.获取模板对象
                Template template = configuration.getTemplate("item.ftl");
                //3.设置模板数据
                Map<String, Object> data = new HashMap<>();
                data.put("product", tProduct);
                //4.生成静态页
                //获取static的路径
                String staticPath = ResourceUtils.getURL("classpath:static").getPath();
                //构建输出流对象
                FileWriter writer = new FileWriter(staticPath + File.separator + id + ".html");
                //生成静态页
                template.process(data, writer);
            } catch (IOException | TemplateException e) {
                e.printStackTrace();
                return new ResultBean("404", "找不到相关页面");
            }
            return new ResultBean("200", "生成静态页成功");
        }
    }

    private ResultBean createHTMLByID(@PathVariable("id") Long id, String s, String 生成静态页成功) {
        //1.获取商品信息
        TProduct tProduct = productService.selectByPrimaryKey(id);
        try {
            //2.获取模板对象
            Template template = configuration.getTemplate("item.ftl");
            //3.设置模板数据
            Map<String, Object> data = new HashMap<>();
            data.put("product", tProduct);
            //4.生成静态页
            //获取static的路径
            String staticPath = ResourceUtils.getURL("classpath:static").getPath();
            //构建输出流对象
            FileWriter writer = new FileWriter(staticPath + File.separator + id + ".html");
            //生成静态页
            template.process(data, writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return new ResultBean("404", "找不到相关页面");
        }
        return new ResultBean("200", 生成静态页成功);
    }
}
