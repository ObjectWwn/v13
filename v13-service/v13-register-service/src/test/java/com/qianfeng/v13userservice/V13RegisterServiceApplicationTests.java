package com.qianfeng.v13userservice;

import com.qianfeng.user.IUserService;
import com.qianfeng.v13.entity.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class V13RegisterServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void contextLoads() {
        TUser tUser = new TUser();
        tUser.setId(22);
        userService.insert(tUser);
    }

}
