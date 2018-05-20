package com.qg.kinectdoctor.model;

/**
 * Created by ZH_L on 2016/10/22.
 */
public class DUser {
    private int id;
    private String name;
    private int sex;
    private int age;
    private String phone;
    private String password;
    private String hospital;
    private String department;//科室
    private String jobTitle;//职称

    public DUser(){};

    public DUser(String name, int sex, int age, String phone, String password, String hospital, String department, String jobTitle) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.hospital = hospital;
        this.department = department;
        this.jobTitle = jobTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "DUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", hospital='" + hospital + '\'' +
                ", department='" + department + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
