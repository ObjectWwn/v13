package com.qianfeng.v13cartweb.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pojo.ResultBean;
import com.qianfeng.login.ISsoService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author wwn
 * @Date 2019/7/1
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Reference
    private ISsoService ssoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断当前用户是否已经登录
        Cookie[] cookies = request.getCookies();
        if ((cookies != null) && cookies.length>0){
            for (Cookie cookie:cookies) {
                if ("user_token".equals(cookie.getName())){
                    String uuid = cookie.getValue();
                    ResultBean resultBean = ssoService.checkIsLogin(uuid);
                    //保存标记
                    if ("200".equals(resultBean.getStatusCode())){
                        request.setAttribute("user",resultBean.getData());
                    }
                }
            }
        }


        //放行
        return true;
    }
}
