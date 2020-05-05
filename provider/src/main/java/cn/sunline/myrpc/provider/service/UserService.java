package cn.sunline.myrpc.provider.service;

import cn.sunline.myrpc.entity.User;
import cn.sunline.myrpc.provider.myinterface.Service;


@Service(serviceId = "userService")
//测试服务
public interface UserService {
    public User findById(String id);
}
