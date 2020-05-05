package cn.sunline.myrpc.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String userid;
    private String accountno;
    private String name;
    private int age;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid='" + userid + '\'' +
                ", accountno='" + accountno + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
