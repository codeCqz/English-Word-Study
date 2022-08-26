package com.cqz.pojo;

public class UserStudyInfo {
        private String way;
        private String level;

    public UserStudyInfo() {
    }

    public UserStudyInfo(String way, String level) {
        this.way = way;
        this.level = level;
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
        return "UserInfo{" +
                "way='" + way + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
