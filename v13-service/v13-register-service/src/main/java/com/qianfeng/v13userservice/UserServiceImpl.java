package com.qianfeng.v13userservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.user.IUserService;
import com.qianfeng.v13.entity.TUser;
import com.qianfeng.v13.mapper.TUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author wwn
 * @Date 2019/6/24
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public IBaseDao getBaseDao() {
        return userMapper;
    }

    @Override
    public int insertSelective(TUser record) {
        //System.out.println("1"+record.getId());null
         super.insertSelective(record);
       // System.out.println("2"+record.getId());id回填了
        return  record.getId();
    }
}
