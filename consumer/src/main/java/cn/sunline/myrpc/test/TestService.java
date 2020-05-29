package cn.sunline.myrpc.test;

import cn.sunline.myrpc.annotation.RpcService;

import java.util.List;

@RpcService
public interface TestService {
    public User getUserById(String id);
    public List<User> getUsers();
    public String sayHello();
    public int getCounts();
}
