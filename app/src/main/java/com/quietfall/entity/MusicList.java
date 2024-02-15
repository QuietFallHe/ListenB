package com.quietfall.entity;

/**
 * 歌单的基本信息：名字
 */
public class MusicList {
    public String name;
    public int id;
    public String fid;


    public MusicList(String name, String fid) {
        this.name = name;
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "MusicList{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }



    public MusicList() {
    }
}
