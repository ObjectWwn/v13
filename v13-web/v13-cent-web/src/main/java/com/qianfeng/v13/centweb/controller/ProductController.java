package com.qianfeng.v13.centweb.controller;

import com.Utils.HttpClientUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pojo.ResultBean;
import com.qianfeng.serarch.ISerarchService;
import com.qianfeng.v13.api.IProductService;
import com.qianfeng.v13.entity.TProduct;
import com.qianfeng.v13.pojo.TProductVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/11
 */
@Controller
@RequestMapping("product")
public class ProductController {

    @Reference //通过注解注入
    private IProductService productService;

    @Autowired
    private RabbitTemplate template;

    @Reference
    private ISerarchService serarchService;

    @RequestMapping("get/{id}")
    @ResponseBody
    public TProduct getById(@PathVariable("id") Long id){
        System.out.println(111);
        System.out.println(id);
        System.out.println(productService);
        return   productService.selectByPrimaryKey(id);
    }

    @RequestMapping("list")
    public String list(Model model){
        //1.获取数据
        List<TProduct> list = productService.list();
        //2.保存数据到model
        model.addAttribute("list",list);
        //3.跳转到页面展示
        return "product/list";
    }

    @RequestMapping("page/{pageIndex}/{pageSize}")
    public String page(@PathVariable("pageIndex") Integer pageIndex,
                       @PathVariable("pageSize") Integer pageSize,
                       Model model){
        PageInfo<TProduct> page = productService.page(pageIndex, pageSize);
        model.addAttribute("page",page);
        return "product/list";
    }

    @PostMapping("delById/{id}")
    @ResponseBody
    public ResultBean delById(@PathVariable("id")  Long id){
        ResultBean resultBean  = new ResultBean();
        int count = productService.deleteById(id);
        //更新索引
        serarchService.synById(id);
        //更新初始化静态化页面
        String url = "http://localhost:9093/item/updateHTML/"+id;
        String s = HttpClientUtils.doGet(url);
        if(count>0){
            resultBean.setStatusCode("200");
            resultBean.setData("删除成功");
            serarchService.synById(id);
        }else{
            resultBean.setStatusCode("404");
            resultBean.setData("删除失败");
        }
        return resultBean;
    }

    @RequestMapping("add")
    public String add( TProductVO productVO){
        //添加商品
        Long save = productService.save(productVO);
        //System.out.println(111);
        template.convertAndSend("product_solr_exchange","add",save);
        //  添加对应的商品索引
        // serarchService.synById(save);
        //添加完要模拟浏览器去发送请求，初始化静态化页面
        //http://localhost:9093/item/createHTML/7
         String url = "http://localhost:9093/item/createHTML/"+save;
         String s = HttpClientUtils.doGet(url);
         System.out.println(s);
        return "redirect:/product/page/1/1";
    }

    //@RequestParam不要直接加，在摩羯特定场合需要
    @PostMapping("batchDel")
    @ResponseBody
    public ResultBean batchDel(List<Long> ids){
        Long count = productService.batchDel(ids);
        System.out.println(ids.size());
        if(count > 0){
            for (int i = 1; i <= ids.size(); i++) {
                serarchService.synById(ids.get(i));
            }
             return  new ResultBean("200", "批量删除成功！");
        }
        return new ResultBean("404","批量删除失败！你懂得！");
    }

    @PostMapping("toUpdate/{id}")
    @ResponseBody
    public TProductVO toUpdate(@PathVariable("id") Long id){
        TProductVO vo = productService.selectVo(id);
        serarchService.synById(id);
        return  vo;
    }

    @PostMapping("update")
    public String upt(TProductVO productVO){
        //System.out.println(productVO.toString());
        productService.updateVo(productVO);
        return "redirect:/product/page/1/1";
    }

}
