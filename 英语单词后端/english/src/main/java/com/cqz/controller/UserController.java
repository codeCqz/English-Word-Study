package com.cqz.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cqz.pojo.User;
import com.cqz.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("/updateuser")
    public Integer updateUser(@RequestParam("form") String form) {
        JSONObject jsonObject = JSON.parseObject(form);
        //获取json对象中的code
        Integer userid = jsonObject.getInteger("userid");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");


        User user = new User(userid,username,password,email);

        userService.updateUser(user);


        return null;
    }

    @RequestMapping("/getuser")
    public User getuser(@RequestParam("userid")int userid) {

        User user = userService.getuser(userid);



        return user;
    }


    @RequestMapping("logout")
    public String logout(){
        Map<String,Object> map=new HashMap<>();
        map.put("code",20000);

        String json=JSON.toJSONString(map);


        return json;
    }




    @RequestMapping("/login")
    public int findUser(HttpServletRequest request, @RequestParam("form") String form) {
        JSONObject jsonObject = JSON.parseObject(form);
        //获取json对象中的code
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        User user = userService.findUser(email,password);
        if (user!=null){
            int id = user.getUserid();


            return id;
        }
        return 404;
    }

    @RequestMapping("/register")
    public String addUser(@RequestParam("form")String form){
        JSONObject jsonObject = JSON.parseObject(form);
        //获取json对象中的code
        System.out.println(jsonObject);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String email = jsonObject.getString("email");
        User user = new User(username,password,email);
        userService.addUser(user);
        return "200";
    }
}
