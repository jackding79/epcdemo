package cn.sunline.myrpc.test;

import cn.sunline.myrpc.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ServiceImpl(ref = TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public User getUserById(String id) {
        User user=new User();
        user.setId(id);
        user.setAge(10);
        user.setName("测试"+id);
        return user;
    }

    @Override
    public List<User> getUsers() {
        User user1=new User();
        User user2=new User();
        user1.setId("1001");
        user1.setAge(10);
        user1.setName("测试1001");
        user2.setId("1002");
        user2.setAge(10);
        user2.setName("测试1002");
        List<User> list=new ArrayList<>();
        list.add(user1);list.add(user2);
        return list;
    }
}
