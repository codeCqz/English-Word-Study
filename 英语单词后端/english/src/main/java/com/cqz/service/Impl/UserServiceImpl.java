package com.cqz.service.Impl;

import com.cqz.dao.UserDao;
import com.cqz.pojo.User;
import com.cqz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUser(String email, String password) {
        return userDao.findUser(email,password);
    }
    @Override
    public void addUser(User user){
        userDao.addUser(user);
    }

    public void setUserState(int userid,String way,Integer count,String level){
        userDao.setUserState(userid,way,count,level);
    }
    public void updateUser(User user){
        userDao.updateUser(user);
    }
    public User getuser(int userid){
        return userDao.getuser(userid);
    }
}
