package com.example.movieapp;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    String username, password, age;
    int credits;
    String id;

    public User() {
    }

    public User(String username, String password, String age, int credits, String id) {
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

