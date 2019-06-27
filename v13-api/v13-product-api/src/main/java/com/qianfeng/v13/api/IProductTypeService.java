package com.qianfeng.v13.api;

import com.qianfeng.IBaseService;
import com.qianfeng.v13.entity.TProductType;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/13
 */
public interface IProductTypeService extends IBaseService<TProductType> {
    public List<TProductType> getTypeList();
}
