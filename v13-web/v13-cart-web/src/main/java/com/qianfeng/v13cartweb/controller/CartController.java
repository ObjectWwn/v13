package com.qianfeng.v13cartweb.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.api.ICartService;
import com.qianfeng.v13.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author wwn
 * @Date 2019/7/1
 */
@Controller
@RequestMapping("cart")
public class CartController {

    @Reference
    private ICartService cartService;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean add(@PathVariable Long productId,
                          @PathVariable Integer count,//可以没有cookie
                          @CookieValue (name = "user_cart",required = false) String uuid,
                          HttpServletResponse response,
                          HttpServletRequest request ){
        TUser user = (TUser) request.getAttribute("user");
        if (user != null){
            //用户属于已登录状态
             uuid = user.getId().toString();
            //2.将商品添加到购物车
            ResultBean resultBean = cartService.add(uuid, productId, count);
            return  resultBean;
        }
        //1.判断当前购物车是否存在
        if(uuid == null || "".equals(uuid)){
            uuid = UUID.randomUUID().toString();
        }

        ResultBean resultBean = cartService.add(uuid, productId, count);
        if ("200".equals(resultBean.getStatusCode())){
            //4.写cookie到客户端
            reflushCookie(uuid,response);
        }
       return resultBean;
    }

    @ResponseBody
    @RequestMapping("delete/{productId}")
    public ResultBean del(@PathVariable Long productId,
                          @CookieValue(name = "user_cart",required = false)String uuid,
                        HttpServletRequest request,
                        HttpServletResponse response){
        TUser user = (TUser) request.getAttribute("user");
        if (user != null){
            //已登录
            uuid = user.getId().toString();
        }
        ResultBean del = cartService.del(uuid, productId);
        if ("200".equals(del.getStatusCode())){
            reflushCookie(uuid,response);
        }
        return  del;
    }

    @ResponseBody
    @RequestMapping("update/{productId}/{count}")
    public ResultBean update(@PathVariable Long productId,
                             @PathVariable Integer count,
                             @CookieValue(name = "user_cart" ,required = false)String uuid,
                             HttpServletResponse response,
                             HttpServletRequest request
    ){
        TUser user = (TUser) request.getAttribute("user");
        if (user!=null) {
            //登录
            uuid = user.getId().toString();
        }
        ResultBean update = cartService.update(uuid, productId, count);
        if ("200".equals(update.getStatusCode())){
            reflushCookie(uuid,response);
        }
        return  update;
    }

    @RequestMapping("get")
    @ResponseBody
    public ResultBean quest(@CookieValue(name = "user_cart",required = false) String uuid,
                            HttpServletResponse response,
                            HttpServletRequest request){
        TUser user = (TUser) request.getAttribute("user");
        if (user !=null ){
            uuid = user.getId().toString();
            //已登录
            ResultBean resultBean= cartService.query(uuid);
            return  resultBean;
        }
        //1.判断
        if (uuid == null || "".equals(uuid)){
            return new ResultBean("404","购物车为空");
        }

        ResultBean query = cartService.query(uuid);
        if ("200".equals(query.getStatusCode())){
            reflushCookie(uuid,response);
        }
        return query;
    }

    private void reflushCookie(@CookieValue(name = "user_cart", required = false) String uuid, HttpServletResponse response) {
        //4.写cookie到客户端
        Cookie cookie = new Cookie("user_cart",uuid);
        cookie.setPath("/");
        //cookie.setDomain("qf.com");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7*24*60*60);
        //
        response.addCookie(cookie);
    }
}
