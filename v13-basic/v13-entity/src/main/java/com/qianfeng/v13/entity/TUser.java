package com.qianfeng.v13.entity;

import java.io.Serializable;

public class TUser implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private Boolean isactivate;

    private String email;

    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getIsactivate() {
        return isactivate;
    }

    public void setIsactivate(Boolean isactivate) {
        this.isactivate = isactivate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
}