package com.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;

/**
 * @Author wwn
 * @Date 2019/6/18
 */
public class HttpClientUtils {

    private HttpClientUtils(){

    }

    public static  String doGet(String url, Map<String,String> params){
        //1.创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //2.定义资源路径
            URIBuilder urlBuilder = new URIBuilder(url);
            if (params!=null){
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    urlBuilder.addParameter(entry.getKey(),entry.getValue());
                }
            }
            //3.创建get对象
            HttpGet get = new HttpGet(urlBuilder.build());
            //4.执行get请求，获取response对象
            CloseableHttpResponse execute = httpClient.execute(get);
            //5.获取响应结果
            int statusCode = execute.getStatusLine().getStatusCode();
            //6.处理结果
            //7.获取响应结果
            HttpEntity entity = execute.getEntity();
            if (200== statusCode){
                return (EntityUtils.toString(entity));
            }else {
                return "error";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static  String doGet(String url){
        return doGet(url,null);
    }


}
