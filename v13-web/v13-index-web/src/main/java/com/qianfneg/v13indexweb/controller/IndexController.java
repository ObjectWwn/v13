package com.qianfneg.v13indexweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qianfeng.v13.api.IProductTypeService;
import com.qianfeng.v13.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/14
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Reference
    private IProductTypeService typeService;

    @RequestMapping("home")
    public  String getHome(Model model){
        List<TProductType> list = typeService.list();
        System.out.println("list"+list.size());
        model.addAttribute("list",list);
        return "index";
    }

    @ResponseBody
    @RequestMapping("list")
    public void getTypeList(Model model){
        List<TProductType> list = typeService.getTypeList();
       // System.out.println("getTypeList"+list.size());
        model.addAttribute("list",list);
    }
}
