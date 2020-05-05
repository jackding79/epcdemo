package cn.sunline.myrpc.consumer;

import cn.sunline.myrpc.provider.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {
    @Autowired
    UserService userService;
    @Autowired
    ApplicationContext applicationContext;
    @Test
    public void contextLoads() {
        System.out.println(  userService.findById("1001").toString());
    }

}
