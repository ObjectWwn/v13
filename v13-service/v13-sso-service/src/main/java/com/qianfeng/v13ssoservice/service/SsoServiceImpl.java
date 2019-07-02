package com.qianfeng.v13ssoservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.pojo.ResultBean;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.login.ISsoService;
import com.qianfeng.v13.entity.TUser;
import com.qianfeng.v13.mapper.TUserMapper;
import com.qianfeng.v13ssoservice.Utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author wwn
 * @Date 2019/6/27
 */
@Service
public class SsoServiceImpl extends BaseServiceImpl<TUser> implements ISsoService {

    //加密
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        //前台的密码与后台的密文比较,顺序一定不能错
        //Encoded password does not look like BCrypt
        boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), tUser.getPassword());
        if(matches){
            //用户合法
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSecretKey("java1902");
            jwtUtils.setTtl(30*60*1000);
            String jwtToken = jwtUtils.createJwtToken(tUser.getId().toString(), tUser.getUsername());
            //传回令牌
            return  new ResultBean("200", jwtToken);
        }
        return new ResultBean("404","");
    }

    //
    @Override
    public ResultBean checkIsLogin(String uuid) {
        try {
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSecretKey("java1902");
            jwtUtils.setTtl(30*60*1000);
            Claims claims = jwtUtils.parseJwtToken(uuid);
            System.out.println("剩余时间"+claims.getIssuedAt());//剩余时间
            //新的token
            String jwtToken = jwtUtils.createJwtToken(claims.getId(), claims.getSubject());
            //还要刷新有效时间
            return new ResultBean("200",jwtToken);
        } catch (Exception e){
            //令牌过期或不正确
            return new ResultBean("404","");
        }

    }

    //删除
   /* @Override
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
    }*/
}
