package com.qianfeng.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author wwn
 * @Date 2019/6/29
 */
public class CartItem implements Serializable {
    private Long productId;

    private Integer count;

    private Date updateDate;

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", count=" + count +
                ", updateDate=" + updateDate +
                '}';
    }

    public CartItem() {
    }

    public CartItem(Long productId, Integer count, Date updateDate) {
        this.productId = productId;
        this.count = count;
        this.updateDate = updateDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
