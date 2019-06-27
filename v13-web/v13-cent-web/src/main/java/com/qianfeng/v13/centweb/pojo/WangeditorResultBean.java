package com.qianfeng.v13.centweb.pojo;

/**
 * @Author wwn
 * @Date 2019/6/14
 */
public class WangeditorResultBean {
    private String errno;
    private String[] data;

    public WangeditorResultBean() {

    }

    public WangeditorResultBean(String errno, String[] data) {
        this.errno = errno;
        this.data = data;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getErrno() {
        return errno;
    }

    public String[] getData() {
        return data;
    }
}
