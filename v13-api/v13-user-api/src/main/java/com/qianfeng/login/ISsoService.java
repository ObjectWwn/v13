package com.qianfeng.login;

import com.pojo.ResultBean;
import com.qianfeng.IBaseService;
import com.qianfeng.v13.entity.TUser;

/**
 * @Author wwn
 * @Date 2019/6/27
 */
public interface ISsoService  extends IBaseService<TUser> {
    //用邮箱或者手机号验证登录
     ResultBean checkLogin(TUser user);

    ResultBean checkIsLogin(String uuid);

    ResultBean logout(String uuid);
}
