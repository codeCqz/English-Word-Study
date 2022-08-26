package com.cqz.pojo;


public class Userword {
    private int userid;
    private String word;
    private int status;
    private String date;
    private String way;
    private String level;

    public Userword() {
    }

    public Userword(int userid, String word, int status, String date, String way, String level) {
        this.userid = userid;
        this.word = word;
        this.status = status;
        this.date = date;
        this.way = way;
        this.level = level;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    @Override
    public String toString() {
        return "Userword{" +
                "userid=" + userid +
                ", word='" + word + '\'' +
                ", status=" + status +
                ", date='" + date + '\'' +
                ", way='" + way + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
