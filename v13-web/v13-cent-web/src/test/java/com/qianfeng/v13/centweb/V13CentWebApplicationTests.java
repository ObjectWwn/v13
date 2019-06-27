package com.qianfeng.v13.centweb;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13CentWebApplicationTests {

    @Autowired
    private FastFileStorageClient fileStorageClient;

    @Test
    public void uploadTest() throws FileNotFoundException {
       /* File file = new File("D:\\idea(WorkSpace)\\v13\\v13-web\\v13-cent-web\\hello.html");
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = fileStorageClient.uploadFile(fileInputStream, file.length(), "html", null);
        System.out.println(storePath.getPath());
        System.out.println(storePath.getGroup());
        System.out.println(storePath.getFullPath());*/

        //图片
        File file = new File("D:\\idea(WorkSpace)\\v13\\v13-web\\v13-cent-web\\1.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        StorePath storePath = fileStorageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), "jpg", null);
        System.out.println(storePath.getFullPath());

    }

    @Test
    public void  deleteTest(){
        fileStorageClient.deleteFile("group1/M00/00/1C/CiQICF0CPzmAIQCRAAyXgwtXd6g518.jpg");
        //删除后在删除会报错
    }

}
