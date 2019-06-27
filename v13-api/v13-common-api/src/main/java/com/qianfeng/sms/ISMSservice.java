package com.qianfeng.sms;

/**
 * @Author wwn
 * @Date 2019/6/26
 */
//发送短信的接口
public interface ISMSservice  {
    public  void  sendSMS(String phoneNumber,String message);
}
