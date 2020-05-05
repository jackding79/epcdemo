package cn.sunline.myrpc.provider.service;

import cn.sunline.myrpc.consumer.annotation.RpcService;
import cn.sunline.myrpc.entity.User;

@RpcService
public interface UserService {
    public User findById(String id);

}
