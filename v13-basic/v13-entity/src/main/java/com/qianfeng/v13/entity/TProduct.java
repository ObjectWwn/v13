package com.qianfeng.v13.entity;

import java.io.Serializable;

public class TProduct implements Serializable {
    @Override
    public String toString() {
        return "TProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", salePrice=" + salePrice +
                ", images='" + images + '\'' +
                ", salePoint='" + salePoint + '\'' +
                ", typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                ", flag=" + flag +
                '}';
    }

    private Long id;

    private String name;

    private Long price;

    private Long salePrice;

    private String images;

    private String salePoint;

    private Long typeId;

    private String typeName;

    private Boolean flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(String salePoint) {
        this.salePoint = salePoint == null ? null : salePoint.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}