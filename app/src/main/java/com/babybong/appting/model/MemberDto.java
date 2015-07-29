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
    private Date createAt;
    private Date updateAt;
    //기본사항
    private String nickName;
    private String address1;
    private String address2;
    private String job;
    private String hobby;
    private String character;   //추가
    private String bloodType; //혈액형
    private String religion; //종교
    private String height;      //추가
    private String bodyType;    //추가

    private String selfIntroduction;
    private String idealType;
    private String myAppeal;
    //선택사항
    private String drinkAndSmoke;
    private String wantDate;
    private String firstPoint;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getIdealType() {
        return idealType;
    }

    public void setIdealType(String idealType) {
        this.idealType = idealType;
    }

    public String getMyAppeal() {
        return myAppeal;
    }

    public void setMyAppeal(String myAppeal) {
        this.myAppeal = myAppeal;
    }

    public String getDrinkAndSmoke() {
        return drinkAndSmoke;
    }

    public void setDrinkAndSmoke(String drinkAndSmoke) {
        this.drinkAndSmoke = drinkAndSmoke;
    }

    public String getWantDate() {
        return wantDate;
    }

    public void setWantDate(String wantDate) {
        this.wantDate = wantDate;
    }

    public String getFirstPoint() {
        return firstPoint;
    }

    public void setFirstPoint(String firstPoint) {
        this.firstPoint = firstPoint;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
}

