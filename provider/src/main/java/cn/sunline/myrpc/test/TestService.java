package cn.sunline.myrpc.test;

import cn.sunline.myrpc.Service;

import java.util.List;

@Service
public interface TestService {
    public User getUserById(String id);
    public List<User> getUsers();
    public String sayHello();
    public int getCounts();

}
