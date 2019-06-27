package com.qianfeng.v13ssoservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pojo.ResultBean;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.login.ISsoService;
import com.qianfeng.v13.entity.TUser;
import com.qianfeng.v13.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author wwn
 * @Date 2019/6/27
 */
@Service
public class SsoServiceImpl extends BaseServiceImpl<TUser> implements ISsoService {

    @Autowired
    private TUserMapper  userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return userMapper;
    }

    //验证登录
    @Override
    public ResultBean checkLogin(TUser user) {
        TUser tUser = userMapper.selectByUsername(user.getUsername());
        if(user.getPassword().equals(tUser.getPassword())){
            //用户合法
            //保存凭证在redis中
            String uuid = UUID.randomUUID().toString();
            String key = new StringBuilder("user:token:").append(uuid).toString();
            //去掉密码
            user.setPassword(null);
            //设置有效期,30分钟
            redisTemplate.opsForValue().set(key,user);
            redisTemplate.expire(key,30, TimeUnit.MINUTES);
            return  new ResultBean("200", uuid);
        }
        return new ResultBean("404","");
    }

    //
    @Override
    public ResultBean checkIsLogin(String uuid) {
        //1.组装key
        String key = new StringBuilder("user:token:").append(uuid).toString();
        //2，查询
        TUser currentUser = (TUser) redisTemplate.opsForValue().get(key);
        if (currentUser!=null){
            //4.刷新凭证的有效期
            redisTemplate.expire(key,30,TimeUnit.MINUTES);
            return new ResultBean("200",currentUser);
        }
        return new ResultBean("404","");
    }

    //删除
    @Override
    public ResultBean logout(String uuid) {
        //1.拼接key
        String key = new StringBuilder("user:token:").append(uuid).toString();
        //删除
        Boolean delete = redisTemplate.delete(key);
        if (delete){
            new ResultBean("200","删除成功");
        }else{
            new ResultBean("404","删除失败");
        }
        return null;
    }
}
