package com.qianfeng.v13ssoweb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.login.ISsoService;
import com.qianfeng.v13.entity.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wwn
 * @Date 2019/6/27
 */
@Controller
@RequestMapping("sso")
public class SsoController {

    @Reference
    private ISsoService ssoService;

    @RequestMapping("showLogin")
    public String show(HttpServletRequest request, Model model){
        //得到登录请求时从哪来的
        String referer = request.getHeader("Referer");
        model.addAttribute("referer",referer);
        return "index";
    }

    //1，登录的认证接口
    //1.1 第一种是返回ResultBean的方式（Ajax）
    //1.2 第二种是跳转到相关页面的方式（form）
    @RequestMapping("checkLogin")
    public String checkLogin(TUser user,
                             String referer,
                             HttpServletResponse response){
        //用户的认证+redis中保存凭证 uuid---user
        ResultBean resultBean = ssoService.checkLogin(user);
        if ("200".equals(resultBean.getStatusCode())){
            //验证成功，Cookie,uuid---user
            //创建Cookie
            Cookie cookie = new Cookie("user_token",resultBean.getData().toString());
            cookie.setPath("/");
            //只有后端可以访问cookie
            cookie.setHttpOnly(true);
            //将这个cookie写到客户端
            response.addCookie(cookie);
            //System.out.println(11);
            //返回一个视图,暂时返回首页
           // return  "redirect:http://localhost:9091/index/home";
            if (referer==null){
                return  "redirect:http://localhost:9091/index/home";
            }
            return "redirect:"+referer;
        }
        //验证失败，回到登录页
        return  "index";
    }


    @RequestMapping("checkIsLogin2")
    @ResponseBody
    public ResultBean checkIsLogin2(@CookieValue(name = "user_token",required = false)
                                                String uuid,
                                    HttpServletResponse response
                                    ){
        //简化获取cookie的方式
        //1.解析cookie，获取到凭证信息uuid
        if(uuid != null){
            System.out.println(uuid);
            //return ssoService.checkIsLogin(uuid);
            ResultBean resultBean = ssoService.checkIsLogin(uuid);
            //验证登录成功，把修改了时间的token重新放在cookie中
            if ("200".equals(resultBean.getStatusCode())){
                Cookie cookie = new Cookie("user_token",resultBean.getData().toString());
                cookie.setPath("/");
                //只有后端可以访问cookie
                cookie.setHttpOnly(true);
                //将这个cookie写到客户端
                response.addCookie(cookie);
                System.out.println("checkIsLogin2");
               return resultBean;
            }

        }
        //3.返回找不到的结果
        return new ResultBean("404",null);
    }

    @RequestMapping("logout")
    @ResponseBody
    public ResultBean logout(@CookieValue(name = "user_token",required = false)
                                         String uuid,
                             HttpServletResponse response){
        if(uuid != null){
            //删除cookie
            Cookie cookie = new Cookie("user_token",uuid);
            cookie.setPath("/");
           // cookie.setDomain("qf.com");父域名
            //设置为0，表示失效
            cookie.setMaxAge(0);
            //把cookie写到客户端
            response.addCookie(cookie);
            //删除redis的凭证
            //return ssoService.logout(uuid);
            return new ResultBean("200","注销成功！");
        }
        return new ResultBean("404","注销失败！");
    }
}
