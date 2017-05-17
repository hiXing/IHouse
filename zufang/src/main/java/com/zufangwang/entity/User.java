package com.zufangwang.entity;

import java.io.Serializable;

/**
 * Created by Francis on 2016/4/14.
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String password;
    private String tel;
    private String headimg;
    public User(int id,String name,String password,String tel,String headimg){
        this.id=id;
        this.name=name;
        this.password=password;
        this.tel=tel;
        this.headimg=headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }
}
