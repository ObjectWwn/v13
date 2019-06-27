package com.qianfeng.v13serachweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.serarch.ISerarchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author wwn
 * @Date 2019/6/17
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @Reference
    private ISerarchService serarchService;

   // private String keyWords;

    @RequestMapping("getByKeyWords")
   // public String serachGetKeyWords(String key,int pageNum,Model model){
    public String serachGetKeyWords(String key, Model model, ModelMap map){
        ResultBean resultBean = serarchService.queryByKeyWords(key);
      //  ResultBean resultBean = serarchService.queryByKeyWords(key,pageNum);
        model.addAttribute("resultBean",resultBean);
        model.addAttribute("key",key);
        return "index";
    }

    @RequestMapping("page/{pageNum}")
    @ResponseBody
    public void  updatePageResultBean(@PathVariable("pageNum") int pageNum){

    }

}
