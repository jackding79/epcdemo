package cn.sunline.myrpc.provider.service.impl;

import cn.sunline.myrpc.entity.User;
import cn.sunline.myrpc.provider.myinterface.ServiceImpl;
import cn.sunline.myrpc.provider.service.UserService;
import org.springframework.stereotype.Component;

@Component
@ServiceImpl(ref = UserService.class)
public class UserServiceImpl implements UserService {
    @Override
    public User findById(String id) {
        User user=new User();
        user.setAccountno(id);
        user.setAge(18);
        user.setName("jack");
        user.setUserid(id);
        return user;
    }
}
