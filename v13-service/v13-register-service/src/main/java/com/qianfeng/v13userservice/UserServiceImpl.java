package com.qianfeng.v13userservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.user.IUserService;
import com.qianfeng.v13.entity.TUser;
import com.qianfeng.v13.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author wwn
 * @Date 2019/6/24
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {
    //加密
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public IBaseDao getBaseDao() {
        return userMapper;
    }

    @Override
    public int insertSelective(TUser record) {
        //加密
        String encode = bCryptPasswordEncoder.encode(record.getPassword());
        record.setPassword(encode);
         super.insertSelective(record);
       // System.out.println("2"+record.getId());id回填了
        return  record.getId();
    }
}
