package com.qianfeng.api;

import com.pojo.ResultBean;

/**
 * @Author wwn
 * @Date 2019/6/29
 */
public interface ICartService  {
    /**
     *
     * @param uuid 购物车标识
     * @param productId 商品id
     * @param count 添加商品数量
     * @return
     */
    public ResultBean add(String uuid,Long productId,Integer count);

    /**
     *
     * @param uuid 购物车标识
     * @param productId 商品id
     * @param count 更新商品数量
     * @return
     */
    public ResultBean update(String uuid,Long productId,Integer count);

    /**
     *
     * @param uuid 购物车标识
     * @return
     */
    public ResultBean query(String uuid);

    /**
     *
     * @param uuid  购物车标识
     * @param productId 删除的商品id
     * @return
     */
    public ResultBean del(String uuid,Long productId);
}
