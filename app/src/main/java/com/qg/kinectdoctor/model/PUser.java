package com.qg.kinectdoctor.model;

import java.io.Serializable;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class PUser implements Serializable{
    private int id;
    private int age;
    private String name;
    private  int sex;
    private String phone;
    private String password;
    private String birth;

    public PUser(){};

    public PUser(int age, String name, int sex, String phone, String password, String birth) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
