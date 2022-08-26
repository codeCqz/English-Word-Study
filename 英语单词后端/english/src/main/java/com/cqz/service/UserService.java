package com.cqz.service;



import com.cqz.pojo.User;


public interface UserService {
    User findUser(String email, String password);
    void addUser(User user);
    void setUserState(int userid,String way,Integer count,String level);
    void updateUser(User user);
    User getuser(int userid);
}
