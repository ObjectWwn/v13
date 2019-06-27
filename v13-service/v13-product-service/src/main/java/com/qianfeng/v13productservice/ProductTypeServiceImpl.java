package com.qianfeng.v13productservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.v13.api.IProductTypeService;
import com.qianfeng.v13.entity.TProductType;
import com.qianfeng.v13.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/13
 */
@Service
public  class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper tProductTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return tProductTypeMapper;
    }

    @Override
    public List<TProductType> list() {
        return tProductTypeMapper.list();
    }

    @Override
    public List<TProductType> getTypeList() {
        return null;
    }
}
