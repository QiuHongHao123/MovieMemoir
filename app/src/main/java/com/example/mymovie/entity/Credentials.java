package com.example.mymovie.entity;

import java.util.Date;

public class Credentials {
    private UserTable userId;
    private String username;
    private String passwordHash;
    private Date signDate;
    public Credentials(String username) {
        this.username = username;
    }
    public UserTable getUserId() {
        return userId;
    }

    public void setUserId(UserTable userId) {
        this.userId=userId;

    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
}
