package com.qianfeng.v13.mapper;

import com.qianfeng.IBaseDao;
import com.qianfeng.v13.entity.TUser;

public interface TUserMapper extends IBaseDao<TUser> {
     TUser selectByUsername(String username);
}