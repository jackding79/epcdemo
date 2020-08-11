package cn.jackding.rpc.core.test;

import cn.jackding.rpc.core.Service;

import java.util.List;

@Service
public interface TestService {
    public User getUserById(String id);
    public List<User> getUsers();
    public String sayHello();
    public int getCounts();

}
