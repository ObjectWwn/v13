package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author wwn
 * @Date 2019/6/12
 */
@Data
@AllArgsConstructor
public class ResultBean<T> implements Serializable {

    private String statusCode;

    private  T data;

    public  ResultBean(){

    }
}
