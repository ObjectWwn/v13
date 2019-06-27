package com.qianfeng.v13productservice;

import com.qianfeng.v13.api.IProductService;
import com.qianfeng.v13.entity.TProduct;
import com.qianfeng.v13.pojo.TProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13ProductServiceApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void contextLoads() {
        //System.out.println(productService.deleteById(1L));
       /* TProductVO productVO = new TProductVO();
        TProduct tProduct = productService.selectByPrimaryKey(1L);
        productVO.setProductDesc("多年开发经验");
        productVO.setProduct(tProduct);
        System.out.println(productVO);*/
        TProductVO vo = productService.selectVo(4L);
        TProduct product = vo.getProduct();
        String desc = vo.getProductDesc();
        System.out.println(product.getId());
        System.out.println(desc);
        System.out.println("-----------------------");
        /*真正修改时前台必须有id传来就OK，mapper方法决定的*/
        vo.setProductDesc("222222222222222");
        productService.updateVo(vo);
    }

}
