package com.cqz.pojo;

public class User {
    private Integer userid;
    private String username;
    private String password;
    private String email;
    private String state;


    public User() {
    }

    public User(String username, String password, String email) {
        this.userid = null;
        this.username = username;
        this.password = password;
        this.email = email;
        this.state = null;
    }
    public User(Integer userid, String username, String password, String email, String state) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.state = state;
    }

    public User(Integer userid, String username, String password, String email) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
