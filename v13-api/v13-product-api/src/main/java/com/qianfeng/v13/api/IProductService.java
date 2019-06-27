package com.qianfeng.v13.api;

import com.github.pagehelper.PageInfo;
import com.qianfeng.IBaseService;
import com.qianfeng.v13.entity.TProduct;
import com.qianfeng.v13.pojo.TProductVO;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/11
 */
public interface IProductService extends IBaseService<TProduct> {
    //额外的服务
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    //逻辑上的删除
    public int deleteById(Long id);

    public Long save(TProductVO vo);

    public  Long batchDel(List<Long> ids);

    public List<TProduct> getTypeList();

    public  TProductVO selectVo(Long id);

    public void updateVo(TProductVO vo);
}
