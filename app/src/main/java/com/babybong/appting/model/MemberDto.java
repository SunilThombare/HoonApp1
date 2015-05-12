package com.babybong.appting.model;

import java.util.Date;

/**
 * Created by hoon on 2015-04-26.
 */
public class MemberDto {
    private String mail;
    private String password;
    private String name;
    private String sex;
    private String phone;
    private String birthday;
    private String deviceRegId;
    private String phoneAuth;
    private Integer age;
    private String kakaoId;
    private String address1;
    private String address2;
    private Date createAt;
    private Date updateAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhoneAuth() {
        return phoneAuth;
    }

    public void setPhoneAuth(String phoneAuth) {
        this.phoneAuth = phoneAuth;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getKakaoId() {
        return kakaoId;
    }

    public void setKakaoId(String kakaoId) {
        this.kakaoId = kakaoId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceRegId() {
        return deviceRegId;
    }

    public void setDeviceRegId(String deviceRegId) {
        this.deviceRegId = deviceRegId;
    }
}

