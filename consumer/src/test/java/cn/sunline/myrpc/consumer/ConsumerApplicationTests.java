package cn.sunline.myrpc.consumer;

import cn.sunline.myrpc.test.TestService;
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
    ApplicationContext applicationContext;
    @Autowired
    TestService testService;
    @Test
    public void contextLoads() {
        System.out.println(testService.getUserById("2"));
        System.out.println(testService.getUsers());
        System.out.println(testService.sayHello());
        System.out.println(testService.getCounts());
    }

}
