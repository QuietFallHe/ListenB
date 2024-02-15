package com.quietfall.entity;

public class Music {
    public Integer id;
    public Long duration;
    public Long cid;

    public String title;
    public String upName;
    public String bv;
    public String cover;


    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", duration=" + duration +
                ", title='" + title + '\'' +
                ", upName='" + upName + '\'' +
                ", bv='" + bv + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }
}
