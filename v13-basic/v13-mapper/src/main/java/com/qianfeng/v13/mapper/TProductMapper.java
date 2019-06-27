package com.qianfeng.v13.mapper;

import com.qianfeng.IBaseDao;
import com.qianfeng.v13.entity.TProduct;

import java.util.List;

public interface TProductMapper extends IBaseDao<TProduct>  {

    public  Long batchDelete(List<Long> ids);


}