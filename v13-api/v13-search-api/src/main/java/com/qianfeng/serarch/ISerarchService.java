package com.qianfeng.serarch;

import com.pojo.ResultBean;

/**
 * @Author wwn
 * @Date 2019/6/17
 */
public interface ISerarchService {
    public ResultBean synAllData();
    public ResultBean synById(Long id);
    public  ResultBean queryByKeyWords(String keyWords);
    public  ResultBean queryByKeyWords(String keyWords,int pageNum);
}
