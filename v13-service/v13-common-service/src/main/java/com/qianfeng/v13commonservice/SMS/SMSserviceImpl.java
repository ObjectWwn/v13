package com.qianfeng.v13commonservice.SMS;

import com.alibaba.dubbo.config.annotation.Service;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qianfeng.sms.ISMSservice;

/**
 * @Author wwn
 * @Date 2019/6/26
 */
@Service
public class SMSserviceImpl implements ISMSservice {

    @Override
    public  void sendSMS(String phoneNumber, String message) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIsbbcAG6ltYMI", "3jtX4PGQtkwKJVkpd8FW0XNZWvA0nu");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "久游云计算");
        request.putQueryParameter("TemplateCode", "SMS_168826409");
        //request.putQueryParameter("TemplateParam", "{\"code\":\"wwn\"}");
        String code = "{\"code\""+":\""+message+"\"}";
        request.putQueryParameter("TemplateParam", code);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
