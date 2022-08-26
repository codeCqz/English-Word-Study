package com.cqz.dao;


import com.cqz.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserDao {
    User findUser(@Param("email")String email, @Param("password")String password);
    void addUser(User user);
    void setUserState(int userid,String way,Integer count,String level);
    void updateUser(User user);
    User getuser(int userid);

}
