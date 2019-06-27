/*
package com.qianfeng.v13.pojo;

import com.qianfeng.v13.entity.TProduct;
import lombok.Data;

import java.io.Serializable;

*/
/**
 * @author huangguizhao
 *//*

@Data
public class TProductVO implements Serializable {

    private TProduct product;

    public void setProduct(TProduct product) {
        product.setName(name);
        product.setImages(images);
        product.setFlag(true);
        product.setPrice(price);
        product.setSalePoint(salePoint);
        product.setSalePrice(salePrice);
        product.setTypeId(typeId);
        product.setTypeName(typeName);
        this.product = product;
    }

    private String name;

    private Long price;

    private Long salePrice;

    private String images;

    private String salePoint;

    private Long typeId;

    private String typeName;


    private String productDesc;

}
*/
package com.qianfeng.v13.pojo;

import com.qianfeng.v13.entity.TProduct;

import java.io.Serializable;

public class TProductVO implements Serializable {

    private TProduct product;
    private String productDesc;

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
