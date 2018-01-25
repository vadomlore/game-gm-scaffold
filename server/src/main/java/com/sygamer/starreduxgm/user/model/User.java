package com.sygamer.starreduxgm.user.model;

import java.sql.Timestamp;

public class User {

    private Integer id;

    private String name;

    private String password;

    private String avatar;

    private String introduction;

    private Timestamp lastLoginTime;

    public User(){

    }

    public User(Integer id, String name, String password, String avatar, String introduction, Timestamp lastLoginTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
        this.introduction = introduction;
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
