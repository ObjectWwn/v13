package com.qianfeng.v13cartservice.service;

import com.Common;
import com.alibaba.dubbo.config.annotation.Service;
import com.pojo.ResultBean;
import com.qianfeng.api.ICartService;
import com.qianfeng.pojo.CartItem;
import com.qianfeng.v13.mapper.TProductMapper;
import com.qianfeng.vo.CartItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author wwn
 * @Date 2019/6/29
 */

@Service
public class CartServiceImpl implements ICartService {

    @Resource(name = "redisTemplate1")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private TProductMapper productMapper;

    @Override
    public ResultBean add(String uuid, Long productId, Integer count) {
        //1,构建一个redis的key
        String key = new StringBuffer(Common.USER_CART).append(uuid).toString();
        //考虑当前的购物车是否已经存在该商品
        CartItem CartItem = (CartItem) redisTemplate.opsForHash().get(key,productId.toString());
        if (CartItem !=null){
            //商品已有叠加
            CartItem.setCount(CartItem.getCount()+count);
            CartItem.setUpdateDate(new Date());
        }else{
            //新建
            CartItem = new CartItem(productId,count,new Date());
        }
        //3.将对象保存到Redis中
        redisTemplate.opsForHash().put(key,productId.toString(),CartItem);
        //4.刷新有效期
        redisTemplate.expire(key,7, TimeUnit.DAYS);
        //5.返回结果
        return new ResultBean("200","添加购物车成功");
    }

    @Override
    public ResultBean update(String uuid, Long productId, Integer count) {
        //1，拼接key
        String key = new StringBuilder(Common.USER_CART).append(uuid).toString();
        CartItem cartItem = (CartItem) redisTemplate.opsForHash().get(key, productId.toString());
        //存在
        if (cartItem != null){
            cartItem.setCount(count);
            cartItem.setUpdateDate(new Date());
            redisTemplate.opsForHash().put(key,productId.toString(),cartItem);

            //刷新整辆购物车的有效期
            redisTemplate.expire(key,7,TimeUnit.DAYS);

            return new ResultBean("200","购物车商品数量更新成功！");
        }
        return new ResultBean("404","购物车商品数量更新失败！");
    }

    @Override
    public ResultBean query(String uuid) {
        //1.构建一个key
        String key = new StringBuffer(Common.USER_CART).append(uuid).toString();
        //2.获取购物车的信息
        Map<Object, Object> cartMap = redisTemplate.opsForHash().entries(key);
        //3.排序的问题
        //hash是没有顺序的，但我们要求购物车的信息是有序的
        //存储排序的结果
        TreeSet<CartItemVO> cart = new TreeSet<>();
        //
        Set<Map.Entry<Object, Object>> entries = cartMap.entrySet();
        //第一部分：组装基本数据
        List<Long> ids = new ArrayList<>();
        //
        for (Map.Entry<Object, Object> entry : entries) {
            CartItem cartItem = (CartItem) entry.getValue();
            //cartItem --> cartItemVO
            CartItemVO cartItemVO = new CartItemVO();
            cartItemVO.setCount(cartItem.getCount());
            cartItemVO.setUpdateTime(cartItem.getUpdateDate());

            cartItemVO.setProduct(productMapper.selectByPrimaryKey(cartItem.getProductId()));
            cart.add(cartItemVO);
        }
        //刷新整辆购物车的有效期
        redisTemplate.expire(key,7,TimeUnit.DAYS);

        return new ResultBean("200",cart);
    }

    @Override
    public ResultBean del(String uuid, Long productId) {
        //1，拼接key
        String key = new StringBuilder(Common.USER_CART).append(uuid).toString();
        //2.查看是否存在该商品
        Long delete = redisTemplate.opsForHash().delete(key, productId.toString());
        //存在
        if (delete>0){
            //刷新整辆购物车的有效期
            redisTemplate.expire(key,7,TimeUnit.DAYS);
            return  new ResultBean("200","删除商品成功");
        }
        return null;
    }
}
