package com.qianfeng.v13.centweb.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.pojo.ResultBean;
import com.qianfeng.v13.centweb.pojo.WangeditorResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author wwn
 * @Date 2019/6/13
 */
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;


    @PostMapping("upload")
    @ResponseBody
    public ResultBean upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();//**.**
        String name = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        try {
            StorePath storePath = client.uploadFile(file.getInputStream(), file.getSize(), name, null);
            String fullPath =new StringBuffer(imageServer).append(storePath.getFullPath()).toString();
            return new ResultBean("200",fullPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean("404","网络不稳定");
    }

    @PostMapping("uploads")
    @ResponseBody
    public  WangeditorResultBean uploads(MultipartFile[] files){
        String[] data = new String[files.length];
        for (int i = 0; i < files.length ; i++) {
            String originalFilename = files[i].getOriginalFilename();//**.**
            String name = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
            try {
                StorePath storePath = client.uploadFile(files[i].getInputStream(), files[i].getSize(), name, null);
                String fullPath =new StringBuffer(imageServer).append(storePath.getFullPath()).toString();
                data[i] = fullPath;
            } catch (IOException e) {
                e.printStackTrace();
                return new WangeditorResultBean("1",null);
            }
        }
        return new WangeditorResultBean("0",data);
    }

}
