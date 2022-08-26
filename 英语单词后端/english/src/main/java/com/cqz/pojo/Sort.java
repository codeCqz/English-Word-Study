package com.cqz.pojo;

public class Sort {
   private int id;
   private String sortname;
   private int sortcount;
   private String level;

    public Sort() {
    }



    public Sort(String sortname, int sortcount) {
        this.sortname = sortname;
        this.sortcount = sortcount;
    }






    public Sort(int id, String sortname, int sortcount, String level) {
        this.id = id;
        this.sortname = sortname;
        this.sortcount = sortcount;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public int getSortcount() {
        return sortcount;
    }

    public void setSortcount(int sortcount) {
        this.sortcount = sortcount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "id=" + id +
                ", sortname='" + sortname + '\'' +
                ", sortcount=" + sortcount +
                ", level='" + level + '\'' +
                '}';
    }
}
