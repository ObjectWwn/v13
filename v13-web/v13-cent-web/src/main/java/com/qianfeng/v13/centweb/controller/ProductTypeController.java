package com.qianfeng.v13.centweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qianfeng.v13.api.IProductTypeService;
import com.qianfeng.v13.entity.TProductType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/13
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController {

    @Reference
    private IProductTypeService typeService;

    @PostMapping("getList")
    @ResponseBody
    public List<TProductType> getTypeList(){
        List<TProductType>  list = typeService.list();
        //System.out.println(list.size());
        return  list;
    }
}
