package com.example.movieapp;

import java.util.UUID;

public class UserHelperClass {

    String username, password, age;
    String credits;
    String id;

    public UserHelperClass() {
    }

    public UserHelperClass(String username, String password, String age, String credits, String id) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.credits = credits;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

