package com.qianfeng.v13productservice;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qianfeng.BaseServiceImpl;
import com.qianfeng.IBaseDao;
import com.qianfeng.v13.api.IProductService;
import com.qianfeng.v13.entity.TProduct;
import com.qianfeng.v13.entity.TProductDesc;
import com.qianfeng.v13.mapper.TProductDescMapper;
import com.qianfeng.v13.mapper.TProductMapper;
import com.qianfeng.v13.pojo.TProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author wwn
 * @Date 2019/6/11
 */
/*@Service*///本地
@Service//发布后
public class ProductServiceImpl  extends BaseServiceImpl<TProduct> implements IProductService {

    @Autowired
    private TProductMapper tproductMapper;

    @Autowired
    private TProductDescMapper tProductDescMapper;

    @Override
    public IBaseDao getBaseDao() {
        return tproductMapper;
    }

    @Override
    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize) {
        //1.设置分页参数
        PageHelper.startPage(pageIndex,pageSize);
        //2.获取数据
        List<TProduct> list = list();
        //3.构建一个分页对象
        PageInfo<TProduct> pageInfo = new PageInfo<TProduct>(list,2);
        return pageInfo;
    }

    @Override
    public int deleteById(Long id) {
        //逻辑上删除将flag改成false
        TProduct tProduct = new TProduct();
        tProduct.setId(id);
        tProduct.setFlag(false);
        //受影响行数
        int count = tproductMapper.updateByPrimaryKeySelective(tProduct);
        return count;
    }

    @Override
    @Transactional//事务控制
    public Long save(TProductVO vo) {
        //1.保存商品的基本信息
        TProduct product = vo.getProduct();
        System.out.println(product.toString());
        product.setFlag(true);
        //主键回填
        int count = tproductMapper.insert(product);
        //2.保存商品的描述信息
        String productDesc = vo.getProductDesc();
        TProductDesc desc = new TProductDesc();
        desc.setProductDesc(productDesc);
        desc.setProductId(product.getId());
        tProductDescMapper.insert(desc);
        //3.返回新增商品的主键
        return product.getId();
    }

    @Override
    public Long batchDel(List<Long> ids) {
        return tproductMapper.batchDelete(ids);
    }

    @Override
    public List<TProduct> getTypeList() {
        List<TProduct> list = tproductMapper.list();
        return list;
    }

    @Override
    public  TProductVO selectVo(Long id){
        TProduct tProduct = tproductMapper.selectByPrimaryKey(id);
        TProductVO tProductVO = new TProductVO();
        tProductVO.setProduct(tProduct);
        tProductVO.setProductDesc(tProductDescMapper.selectByPrimaryKey(id).getProductDesc());
        return  tProductVO;
    }

    @Override
    public void updateVo(TProductVO vo) {
        tproductMapper.updateByPrimaryKeySelective(vo.getProduct());
        TProductDesc desc = new TProductDesc();
        desc.setProductId(vo.getProduct().getId());
        desc.setProductDesc(vo.getProductDesc());
        tProductDescMapper.updateByPrimaryKeySelective(desc);
    }
}
